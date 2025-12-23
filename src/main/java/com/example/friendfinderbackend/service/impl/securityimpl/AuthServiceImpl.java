package com.example.friendfinderbackend.service.impl.securityimpl;

import com.example.friendfinderbackend.controller.vm.securityvm.AuthRequestVm;
import com.example.friendfinderbackend.controller.vm.securityvm.AuthResponseVm;
import com.example.friendfinderbackend.mapper.securitymapper.AccountMapper;
import com.example.friendfinderbackend.model.security.RoleCode;
import com.example.friendfinderbackend.repo.securityrepo.AccountRepo;
import com.example.friendfinderbackend.service.securityservice.AuthService;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import com.example.friendfinderbackend.service.dto.securitydto.RoleDto;
import com.example.friendfinderbackend.service.securityservice.AccountService;
import com.example.friendfinderbackend.service.securityservice.token.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final AccountRepo accountRepo;
    private AccountService accountService;
   private TokenHandler tokenHandler;
   private PasswordEncoder passwordEncoder;

   @Autowired
    public AuthServiceImpl(AccountService accountService, TokenHandler tokenHandler, PasswordEncoder passwordEncoder, AccountRepo accountRepo) {
        this.accountService = accountService;
        this.tokenHandler = tokenHandler;
        this.passwordEncoder = passwordEncoder;
       this.accountRepo = accountRepo;
   }

    @Override
    public AuthResponseVm login(AuthRequestVm authRequestVm) {
        AccountDto accountDto = accountService.getAccountByEmail(authRequestVm.getEmail());
                if (!passwordEncoder.matches(authRequestVm.getPassword(), accountDto.getPassword())) {
                    throw new RuntimeException("invalid.password");
                }
          AuthResponseVm authResponseVm = AccountMapper.ACCOUNT_MAPPER.toAuthResponseVm(accountDto);
                authResponseVm.setToken(tokenHandler.createToken(accountDto));
                authResponseVm.setUserRoles(getAccountRoles(accountDto));
                authResponseVm.setAccountId(accountDto.getId());
                return authResponseVm;


    }

    @Override
    @CacheEvict(value = "accounts" , allEntries = true)
    public AuthResponseVm signup(AccountDto accountDto) {
       AccountDto savedAccountDto = accountService.createAccount(accountDto);
       if(Objects.isNull(savedAccountDto)){
           throw new RuntimeException("account.failed");
       }
    AuthResponseVm authResponseVm = AccountMapper.ACCOUNT_MAPPER.toAuthResponseVm(savedAccountDto);
       authResponseVm.setToken(tokenHandler.createToken(savedAccountDto));
       authResponseVm.setUserRoles(getAccountRoles(savedAccountDto));
       return authResponseVm;
    }
    private List<RoleCode> getAccountRoles(AccountDto accountDto) {
        return accountDto.getRoles().stream().map(RoleDto::getCode).collect(Collectors.toList());
    }
}

