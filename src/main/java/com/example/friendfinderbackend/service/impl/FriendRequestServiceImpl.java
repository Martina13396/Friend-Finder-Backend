package com.example.friendfinderbackend.service.impl;

import com.example.friendfinderbackend.mapper.FriendRequestMapper;
import com.example.friendfinderbackend.mapper.securitymapper.AccountMapper;
import com.example.friendfinderbackend.model.FriendRequest;
import com.example.friendfinderbackend.model.FriendRequestStatus;
import com.example.friendfinderbackend.model.security.Account;
import com.example.friendfinderbackend.repo.FriendRequestRepo;
import com.example.friendfinderbackend.repo.securityrepo.AccountRepo;
import com.example.friendfinderbackend.service.FriendRequestService;
import com.example.friendfinderbackend.service.dto.FriendRequestDto;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    private FriendRequestRepo friendRequestRepo;
    private AccountRepo accountRepo;
    private FriendRequestMapper friendRequestMapper;
    private AccountMapper accountMapper;

    @Autowired
    public FriendRequestServiceImpl(FriendRequestRepo friendRequestRepo, AccountRepo accountRepo, FriendRequestMapper friendRequestMapper, AccountMapper accountMapper) {
        this.friendRequestRepo = friendRequestRepo;
        this.accountRepo = accountRepo;
        this.friendRequestMapper = friendRequestMapper;
        this.accountMapper = accountMapper;
    }

    @Override
    @CacheEvict(value = "friend" , allEntries = true)
    public FriendRequestDto sendFriendRequest(Long receiverId) {
        AccountDto senderDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account sender = accountMapper.toAccount(senderDto);
        Account receiver = accountRepo.findById(receiverId).orElseThrow(() -> new RuntimeException("account.not.found"));

        if (friendRequestRepo.existsBySenderAndReceiver(sender, receiver)) {
            throw new RuntimeException("friendRequest.exists");
        }
        FriendRequest request = new FriendRequest();

        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(FriendRequestStatus.PENDING);
        friendRequestRepo.save(request);
        return friendRequestMapper.toFriendRequestDto(request);

    }

    @Override
    @Caching(
         evict = {
                 @CacheEvict(value = "friend" , allEntries = true),
                 @CacheEvict(value = "post" , allEntries = true)
         }
    )
    public void acceptFriendRequest(Long requestId) {

        FriendRequest request = friendRequestRepo.findById(requestId).orElseThrow(() -> new RuntimeException("request.not.found"));
        request.setStatus(FriendRequestStatus.ACCEPTED);

        request.setRead(false);
        friendRequestRepo.save(request);

        Account sender = request.getSender();
        Account receiver = request.getReceiver();

        sender.setFollowersCount(sender.getFollowersCount() == null? 0: sender.getFollowersCount() + 1);
        receiver.setFollowersCount(receiver.getFollowersCount() == null? 0:receiver.getFollowersCount()+1);
        accountRepo.save(sender);
        accountRepo.save(receiver);



    }



    @Override
    @CacheEvict(value = "friend" , allEntries = true)
    public void rejectFriendRequest(Long requestId) {

        FriendRequest request = friendRequestRepo.findById(requestId).orElseThrow(()->new RuntimeException("request.not.found"));


        friendRequestRepo.delete(request);

    }

    @Override
    @Cacheable(value = "friend" , key = "#root.target.getCurrentUserId()+'requests'")
    public List<FriendRequestDto> getFriendRequestsForUser() {
    Long currentUserId = getCurrentUserId();
    List<FriendRequest> requests = friendRequestRepo.findAllByReceiverIdAndStatus(currentUserId, FriendRequestStatus.PENDING);

    return requests.stream().map(friendRequestMapper::toFriendRequestDto).collect(Collectors.toList());


    }

    @Override
    @Cacheable(value = "friend" , key =  "#root.target.getCurrentUserId() + 'friends'")
    public List<AccountDto> getFriendsForUser() {
      Long currentAccountId = getCurrentUserId();
      List<Long> friendIds = friendRequestRepo.findAcceptedFriendsId(currentAccountId);
      List<Account> accounts = accountRepo.findAllById(friendIds);
      return accounts.stream().map(accountMapper::toAccountDto).collect(Collectors.toList());
    }

    @Override

    public Long getFriendRequestCountBySender() {

        Long accountId = getCurrentUserId();
        return friendRequestRepo.countBySenderIdAndStatusAndReadIsFalse(accountId, FriendRequestStatus.ACCEPTED);
    }

    @Override
    @Cacheable(value = "friend" , key = "#root.target.getCurrentUserId()+'toFollow'")
    public List<AccountDto> getWhoToFollow() {
     Long currentId = getCurrentUserId();
       List<Account> accounts = accountRepo.findAccountsNotFriendsOrRequested(currentId);
       return accounts.stream().map(accountMapper::toAccountDto).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "friend" , key = "#accountId")
    public List<AccountDto> getFriendsForFriend(Long accountId) {
        List<Long> friendIds = friendRequestRepo.findAcceptedFriendsId(accountId);
        List<Account> accounts = accountRepo.findAllById(friendIds);
        return accounts.stream().map(accountMapper::toAccountDto).collect(Collectors.toList());

    }

    @Override
    @Cacheable(value = "friend" , key = "#root.target.getCurrentUserId()+'senderRequest'")
    public List<FriendRequestDto> getFriendRequestsBySender() {

        Long currentUserId = getCurrentUserId();
       List<FriendRequest> requests = friendRequestRepo.findAllBySenderIdAndStatus(currentUserId, FriendRequestStatus.PENDING);
       return requests.stream().map(friendRequestMapper::toFriendRequestDto).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "friends" , key = "#root.target.getCurrentUserId()+'acceptedRequests'")
    public List<FriendRequestDto> getAcceptedFriendRequests() {

        Long currentUserId = getCurrentUserId();
        List<FriendRequest> notifications = friendRequestRepo.findAllBySenderIdAndStatus(currentUserId, FriendRequestStatus.ACCEPTED);
        return notifications.stream().map(friendRequestMapper::toFriendRequestDto).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "friend" , allEntries = true )
    public void markNotificationRead(Long accountId) {
        friendRequestRepo.markRead(accountId);
    }

    @Override
    @Cacheable(value = "friend" , key = "#root.target.getCurrentUserId()+'requestCount'")
    public Long getUnreadRequestCount() {
       AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       return friendRequestRepo.countByReceiverIdAndStatusAndReadIsFalse(accountDto.getId(), FriendRequestStatus.PENDING);
    }

    public Long getCurrentUserId() {
        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountDto.getId();
    }
}
