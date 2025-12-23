package com.example.friendfinderbackend.service.impl;

import com.example.friendfinderbackend.controller.vm.ReactRequestVm;
import com.example.friendfinderbackend.mapper.ReactsMapper;
import com.example.friendfinderbackend.mapper.securitymapper.AccountMapper;
import com.example.friendfinderbackend.model.ReactType;
import com.example.friendfinderbackend.model.Reacts;
import com.example.friendfinderbackend.model.security.Account;
import com.example.friendfinderbackend.repo.PostsRepo;
import com.example.friendfinderbackend.repo.ReactsRepo;
import com.example.friendfinderbackend.service.ActionService;
import com.example.friendfinderbackend.service.ReactsService;
import com.example.friendfinderbackend.service.dto.ReactsDto;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReactsServiceImpl implements ReactsService {

    private ReactsRepo  reactsRepo;
    private ReactsMapper reactsMapper;
    private PostsRepo postsRepo;
    private AccountMapper accountMapper;
    private ActionService  actionService;

    @Autowired
    public ReactsServiceImpl(ReactsRepo reactsRepo, ReactsMapper reactsMapper, PostsRepo postsRepo, AccountMapper accountMapper,ActionService actionService) {
        this.reactsRepo = reactsRepo;
        this.reactsMapper = reactsMapper;
        this.postsRepo = postsRepo;
        this.accountMapper = accountMapper;
        this.actionService = actionService;
    }

    @Override
    @CacheEvict(value = "react" , key = "#reactRequestVm.postId")
    public ReactsDto toggleReacts(ReactRequestVm reactRequestVm) {
        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        long currentAccountId = accountDto.getId();
        Account currentAccount = accountMapper.toAccount(accountDto);

        Optional<Reacts> existingReact = reactsRepo.findByAccountIdAndPostId(currentAccountId, reactRequestVm.getPostId());
        if (existingReact.isPresent()) {
            Reacts react = existingReact.get();
            if(react.getReactType() == reactRequestVm.getReactType()) {
                reactsRepo.delete(react);
                return null;
            }

            react.setReactType(reactRequestVm.getReactType());
            Reacts updatedReact = reactsRepo.save(react);

            return reactsMapper.toReactsDto(updatedReact);

        }

        Reacts reacts = new Reacts();
        reacts.setReactType(reactRequestVm.getReactType());
        reacts.setAccount(accountMapper.toAccount(accountDto));
        reacts.setPost(postsRepo.findById(reactRequestVm.getPostId()).orElseThrow());
        reactsRepo.save(reacts);
        String message = "";
        switch (reactRequestVm.getReactType()) {
            case LIKE -> message = "liked a post";
            case SAD ->  message = "reacted sad to a post";
            case ANGRY -> message = "reacted angry to a post";
            case LOVE ->  message = "loved a post";
            case HAHA -> message = "reacted haha to a post";
            case WOW -> message = "reacted wow to a post";
        }
       actionService.save(currentAccount,message);

       return reactsMapper.toReactsDto(reacts);

    }

    @Override
    @Cacheable(value = "react" , key = "#postId")
    public Map<ReactType,Integer> getAllReactsCountForPost(long postId ) {
        List<Object[]> result = reactsRepo.countReactsByType(postId);
        Map<ReactType,Integer> reactCounts = new HashMap<>();

        for(ReactType reactType : ReactType.values()) {
            reactCounts.put(reactType , 0);

        }
        for(Object[] row : result) {
            ReactType reactType = (ReactType) row[0];
            Long count = (Long) row[1];
            reactCounts.put(reactType , count.intValue());

        }
        return reactCounts;
    }
    public Long getCurrentUserId() {
        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountDto.getId();
    }
}
