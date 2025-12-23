package com.example.friendfinderbackend.service;

import com.example.friendfinderbackend.model.AccountDetails;
import com.example.friendfinderbackend.service.dto.AccountDetailsDto;

public interface AccountDetailsService {

    AccountDetailsDto findAccountDetails();

    AccountDetailsDto updateAccountDetails( AccountDetailsDto accountDetailsDto);

    AccountDetailsDto getFriendAccountDetails(Long accountId);
}
