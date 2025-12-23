package com.example.friendfinderbackend.service;

import com.example.friendfinderbackend.service.dto.FriendRequestDto;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;

import java.util.List;

public interface FriendRequestService {

    FriendRequestDto sendFriendRequest(Long receiverId);

    void acceptFriendRequest(Long requestId);

    void rejectFriendRequest(Long requestId);

    List<FriendRequestDto> getFriendRequestsForUser();

    List<AccountDto> getFriendsForUser();

    Long getFriendRequestCountBySender();

    List<AccountDto> getWhoToFollow();

    List<AccountDto> getFriendsForFriend(Long accountId);

    List <FriendRequestDto>getFriendRequestsBySender ();

    List<FriendRequestDto> getAcceptedFriendRequests ();

    void markNotificationRead(Long accountId);

    Long getUnreadRequestCount();
}
