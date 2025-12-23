package com.example.friendfinderbackend.service.securityservice;

import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;

public interface AccountService {

    AccountDto getAccountByEmail(String email) ;

    AccountDto createAccount(AccountDto accountDto);
}
