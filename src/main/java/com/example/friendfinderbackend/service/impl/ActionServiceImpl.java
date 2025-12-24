package com.example.friendfinderbackend.service.impl;

import com.example.friendfinderbackend.mapper.ActionMapper;
import com.example.friendfinderbackend.model.Action;
import com.example.friendfinderbackend.model.Comments;
import com.example.friendfinderbackend.model.Posts;
import com.example.friendfinderbackend.model.Reacts;
import com.example.friendfinderbackend.model.security.Account;
import com.example.friendfinderbackend.repo.ActionRepo;
import com.example.friendfinderbackend.service.ActionService;
import com.example.friendfinderbackend.service.dto.ActionDto;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ActionServiceImpl implements ActionService {

    private ActionRepo actionRepo;
    private ActionMapper actionMapper;

    @Autowired
    public ActionServiceImpl(ActionRepo actionRepo, ActionMapper actionMapper) {
        this.actionRepo = actionRepo;
        this.actionMapper = actionMapper;
    }

    @Override
    @Cacheable(value = "action" , key = "#root.target.getCurrentUserId()+'top5'")
    public List<ActionDto> getTop5() {
        Long currentAccountId = getCurrentUserId();

        List<Action> actions = actionRepo.findTop5ByAccountIdOrderByCreatedAtDesc(currentAccountId);

        if (actions.isEmpty()) {
            return new ArrayList<>();
        }
        return actions.stream().map(action -> actionMapper.toActionDto(action)).collect(Collectors.toList());

    }

    @Override
    @Cacheable(value = "action" , key = "#accountId + 'friendTop5'")
    public List<ActionDto> getTop5ForFriend(Long accountId) {
     List<Action> actions = actionRepo.findTop5ByAccountIdOrderByCreatedAtDesc(accountId);
     return actions.stream().map(action -> actionMapper.toActionDto(action)).collect(Collectors.toList());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "action", key = "#account.id + 'top5'"),
            @CacheEvict(value = "action", key = "#account.id + 'friendTop5'")
    })
    public ActionDto save(Account account, String actionText) {

        Action action = new Action();
        action.setAction(actionText);
        action.setAccount(account);
        actionRepo.save(action);
        return actionMapper.toActionDto(action);

    }
    public Long getCurrentUserId() {
        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountDto.getId();
    }
}
