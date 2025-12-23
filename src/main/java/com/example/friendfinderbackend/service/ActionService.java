package com.example.friendfinderbackend.service;

import com.example.friendfinderbackend.model.Action;
import com.example.friendfinderbackend.model.security.Account;
import com.example.friendfinderbackend.service.dto.ActionDto;

import java.util.List;

public interface ActionService {

    List<ActionDto> getTop5();
    List<ActionDto> getTop5ForFriend(Long accountId);

    ActionDto save(Account account , String actionText  );
}
