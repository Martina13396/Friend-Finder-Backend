package com.example.friendfinderbackend.service.dto;

import com.example.friendfinderbackend.model.FriendRequestStatus;
import com.example.friendfinderbackend.model.security.Account;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDto {

    private Long id;

    private AccountDto sender;

    private AccountDto receiver;

    private FriendRequestStatus status = FriendRequestStatus.PENDING;

    private String accountEmail;

    boolean read = false;

    private String createdAt;
}
