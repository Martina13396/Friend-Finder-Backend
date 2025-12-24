package com.example.friendfinderbackend.service.impl;

import com.example.friendfinderbackend.mapper.PostsMapper;
import com.example.friendfinderbackend.mapper.securitymapper.AccountMapper;
import com.example.friendfinderbackend.model.Posts;
import com.example.friendfinderbackend.model.security.Account;
import com.example.friendfinderbackend.repo.FriendRequestRepo;
import com.example.friendfinderbackend.repo.PostsRepo;
import com.example.friendfinderbackend.repo.securityrepo.AccountRepo;
import com.example.friendfinderbackend.service.ActionService;
import com.example.friendfinderbackend.service.PostService;
import com.example.friendfinderbackend.service.dto.PostsDto;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostsServiceImpl implements PostService {

    private final AccountMapper accountMapper;
    private PostsRepo  postsRepo;
    private PostsMapper postsMapper;
    private AccountRepo accountRepo;
    private FriendRequestRepo  friendRequestRepo;
    private ActionService  actionService;
    private static final String TIMELINE_VIEW = "timeline";
    private static final String PROFILE_VIEW = "profile";

    @Autowired
    public PostsServiceImpl(PostsRepo postsRepo, PostsMapper postsMapper, AccountRepo accountRepo, FriendRequestRepo friendRequestRepo, AccountMapper accountMapper , ActionService actionService) {
        this.postsRepo = postsRepo;
        this.postsMapper = postsMapper;
        this.accountRepo = accountRepo;
        this.friendRequestRepo = friendRequestRepo;
        this.accountMapper = accountMapper;
        this.actionService = actionService;
    }

    @Override
    @CacheEvict(value = "post" , allEntries = true)
    public PostsDto createPost(PostsDto postsDto) {

        Posts post = postsMapper.toPosts(postsDto);
        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account currentAccount = accountMapper.toAccount(accountDto);

        post.setAccount(currentAccount);



        if (accountDto == null) {
            throw new RuntimeException("account.not.found");
        }
        postsRepo.save(post);
        PostsDto postResult = postsMapper.toPostsDto(post);

            actionService.save(currentAccount,"created a post");

        return postResult;




    }

    @Override
    @Cacheable(value = "post" , key = "#root.target.getCurrentUserId()+'allPosts'")
    public List<PostsDto> getPostsForUserAndFriends() {

        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentAccountId = accountDto.getId();
        List<Long> friendIds = friendRequestRepo.findAcceptedFriendsId(currentAccountId);
        friendIds.add(currentAccountId);
       List<Posts> posts = postsRepo.findAllByUserAndFriendsAndDeletedIsFalse(currentAccountId,friendIds );
       return posts.stream().map(post -> postsMapper.toPostsDto(post)).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "post" , key = "#root.target.getCurrentUserId()+'userPosts'")
    public List<PostsDto> getPostsByAccountId() {


        Long currentAccountId = getCurrentUserId();
        List<Posts> posts = postsRepo.findAllByAccountIdAndDeletedIsFalseOrderByCreatedAtDesc(currentAccountId );

        return posts.stream().map(post -> postsMapper.toPostsDto(post)).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "post" , allEntries = true)
    public List<PostsDto> deletePost(Long postId , String viewType) {

        Long currentAccountId = getCurrentUserId();
        Posts post = postsRepo.findById(postId).orElseThrow(() -> new IllegalArgumentException("post.not.found"));

        Long postOwnerId = post.getAccount().getId();
        boolean isPostOwnerId = (postOwnerId == currentAccountId);
        if (isPostOwnerId) {
            post.setDeleted(true);
            postsRepo.save(post);

        if (TIMELINE_VIEW.equalsIgnoreCase(viewType)) {
            return getPostsForUserAndFriends();
        } else if (PROFILE_VIEW.equalsIgnoreCase(viewType)) {
            return getPostsByAccountId();

        } else {
            return getPostsForUserAndFriends();
        }
    }

   return getPostsForUserAndFriends();
    }

    @Override
    @Cacheable(value = "post" , key = "#root.target.getCurrentUserId()+'allMedia'")
    public List<Map<String, Object>> getAllMediaUrls() {

        Long currentAccountId = getCurrentUserId();
        return getMaps(currentAccountId);


    }

    @Override
    @Cacheable(value = "post" , key = "#accountId + 'album'")
    public List<Map<String, Object>> getFriendAlbum(Long accountId) {
        return getMaps(accountId);
    }


    private List<Map<String, Object>> getMaps(Long accountId) {
        List<Object[]> results = postsRepo.findAlbumMediaByAccountId(accountId);
        List<Map<String, Object>> album = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("url", row[0]);
            item.put("type", row[1]);
            album.add(item);
        }
        return album;
    }

    @Override
    @Cacheable(value = "post" , key = "#accountId + 'posts'")
    public List<PostsDto> getFriendPosts(Long accountId) {
       List<Posts> posts = postsRepo.findAllByAccountIdAndDeletedIsFalseOrderByCreatedAtDesc(accountId);
       return posts.stream().map(post -> postsMapper.toPostsDto(post)).collect(Collectors.toList());

    }

    public Long getCurrentUserId() {
        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountDto.getId();
    }
}
