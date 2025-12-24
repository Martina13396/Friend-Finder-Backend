package com.example.friendfinderbackend.service.impl;

import com.example.friendfinderbackend.mapper.CommentsMapper;
import com.example.friendfinderbackend.mapper.securitymapper.AccountMapper;
import com.example.friendfinderbackend.model.Comments;
import com.example.friendfinderbackend.model.Posts;
import com.example.friendfinderbackend.model.security.Account;
import com.example.friendfinderbackend.repo.CommentsRepo;
import com.example.friendfinderbackend.repo.PostsRepo;
import com.example.friendfinderbackend.service.ActionService;
import com.example.friendfinderbackend.service.CommentsService;
import com.example.friendfinderbackend.service.dto.CommentsDto;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentsServiceImpl implements CommentsService {
    private CommentsMapper commentsMapper;
    private CommentsRepo commentsRepo;
    private PostsRepo postsRepo;
    private AccountMapper accountMapper;
    private ActionService  actionService;

    @Autowired
    public CommentsServiceImpl(CommentsMapper commentsMapper, CommentsRepo commentsRepo, PostsRepo postsRepo, AccountMapper accountMapper, ActionService actionService) {
        this.commentsMapper = commentsMapper;
        this.commentsRepo = commentsRepo;
        this.postsRepo = postsRepo;
        this.accountMapper = accountMapper;
        this.actionService = actionService;
    }

    @Override
    @CacheEvict(value = "comments" , key = "#commentsDto.postId")
    public CommentsDto addComment(CommentsDto commentsDto) {

        Optional<Posts> posts = postsRepo.findById(commentsDto.getPostId());
        if (!posts.isPresent()) {
            throw new RuntimeException("post.not.found");
        }

        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account currentAccount = accountMapper.toAccount(accountDto);
         Comments comments = commentsMapper.toComments(commentsDto);

       comments.setPost(posts.get());
       comments.setAccount(accountMapper.toAccount(accountDto));
        commentsRepo.save(comments);

       actionService.save(currentAccount,"commented on a post");

      return commentsMapper.toCommentsDto(comments);
    }

    @Override
    @Cacheable(value = "comments" , key = "#postId")
    public List<CommentsDto> getCommentsForPost(Long postId) {
        return commentsRepo.findByPostIdOrderByCreatedAtDesc(postId).stream().map(
                commentsMapper::toCommentsDto).collect(Collectors.toList());

    }

    @Override
    @CacheEvict(value = "comments" , allEntries = true)
    public void deleteComment(Long commentId) {
        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long accountId = accountDto.getId();
        Comments comments = commentsRepo.findById(commentId).orElseThrow(()->new RuntimeException("comment.not.found"));



        Long commentOwnerId = comments.getAccount().getId();
        Long postOwnerId = comments.getPost().getAccount().getId();

        boolean isCommentOwnerId = (commentOwnerId == accountId);
        boolean isPostOwnerId = (postOwnerId == accountId);

        if (isCommentOwnerId || isPostOwnerId) {
            commentsRepo.deleteById(commentId);
        }else {
            throw new RuntimeException("not.authorized.delete.comment");
        }

    }
}
