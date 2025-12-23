package com.example.friendfinderbackend.service.impl;

import com.example.friendfinderbackend.controller.vm.SearchResultVm;
import com.example.friendfinderbackend.mapper.CommentsMapper;
import com.example.friendfinderbackend.mapper.PostsMapper;
import com.example.friendfinderbackend.mapper.securitymapper.AccountMapper;
import com.example.friendfinderbackend.model.Comments;
import com.example.friendfinderbackend.model.Posts;
import com.example.friendfinderbackend.model.security.Account;
import com.example.friendfinderbackend.repo.CommentsRepo;
import com.example.friendfinderbackend.repo.PostsRepo;
import com.example.friendfinderbackend.repo.securityrepo.AccountRepo;
import com.example.friendfinderbackend.service.SearchService;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private PostsRepo postsRepo;
    private AccountRepo accountRepo;
    private CommentsRepo commentsRepo;
    private PostsMapper postsMapper;
    private CommentsMapper commentsMapper;
    private AccountMapper accountMapper;

    @Autowired
    public SearchServiceImpl(PostsRepo postsRepo, AccountRepo accountRepo, CommentsRepo commentsRepo, PostsMapper postsMapper, CommentsMapper commentsMapper, AccountMapper accountMapper) {
        this.postsRepo = postsRepo;
        this.accountRepo = accountRepo;
        this.commentsRepo = commentsRepo;
        this.postsMapper = postsMapper;
        this.commentsMapper = commentsMapper;
        this.accountMapper = accountMapper;
    }

    @Override
    public SearchResultVm searchEveryThing(String query) {
     SearchResultVm searchResultVm = new SearchResultVm();
        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long accountId = accountDto.getId();
        List<Posts> posts = postsRepo.searchPosts(query);
        searchResultVm.setPosts(postsMapper.toPostsDtos(posts));

        List<Comments>comments = commentsRepo.findByTextContainingIgnoreCaseOrderByCreatedAtDesc(query);
        searchResultVm.setComments(commentsMapper.toCommentsDtos(comments));

        List<Account>accounts = accountRepo.searchNewPeople(query,accountId);
        searchResultVm.setAccounts(accountMapper.toAccountDtos(accounts));

        return searchResultVm;


    }
}
