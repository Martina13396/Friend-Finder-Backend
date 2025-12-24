package com.example.friendfinderbackend.service.impl.securityimpl;

import com.example.friendfinderbackend.mapper.securitymapper.AccountMapper;
import com.example.friendfinderbackend.model.security.Account;
import com.example.friendfinderbackend.model.security.Role;
import com.example.friendfinderbackend.repo.securityrepo.AccountRepo;
import com.example.friendfinderbackend.repo.securityrepo.RoleRepo;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import com.example.friendfinderbackend.service.dto.securitydto.RoleDto;
import com.example.friendfinderbackend.service.securityservice.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final RoleRepo roleRepo;
    private AccountRepo accountRepo;
    private AccountMapper accountMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepo accountRepo, AccountMapper accountMapper, @Lazy PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    @Override
    @Cacheable(value = "accounts" , key = "#email")
    public AccountDto getAccountByEmail(String email) {
        Optional<Account> account = accountRepo.getAccountByEmailIgnoreCase(email);

        if (!account.isPresent()) {
            throw  new RuntimeException("account.not.found");
        }
       return accountMapper.toAccountDto(account.get());
    }

    @Override
    @CacheEvict(value = "accounts" , allEntries = true)
    public AccountDto createAccount(AccountDto accountDto) {
     if(Objects.nonNull(accountDto.getId())){
         throw new RuntimeException("id.null");

     }

        if (accountRepo.getAccountByEmailIgnoreCase(accountDto.getEmail()).isPresent()) {

            throw new RuntimeException("account.exist");

        }
     Account account = accountMapper.toAccount(accountDto);
     account.setPassword(passwordEncoder.encode(accountDto.getPassword()));

     List<Role> rolesToAssign = new ArrayList<>();
     if(accountDto.getRoles()!=null && !accountDto.getRoles().isEmpty()){
         for(RoleDto  roleDto:accountDto.getRoles()){
             Role foundRole = roleRepo.findByCode(roleDto.getCode());
             if(foundRole!=null){
                 rolesToAssign.add(foundRole);
             }else{
             throw  new RuntimeException("roles.not.exist");
             }
         }
     }
     account.setRoles(rolesToAssign);
     accountRepo.save(account);
     return accountMapper.toAccountDto(account);
    }


}
