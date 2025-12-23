package com.example.friendfinderbackend.controller.vm.securitycontroller;

import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import com.example.friendfinderbackend.service.securityservice.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/createAccount")
    ResponseEntity<AccountDto> createAccount(@RequestBody @Valid AccountDto accountDto){
        return ResponseEntity.ok(accountService.createAccount(accountDto));

    }

    @GetMapping("/getByEmail")

    ResponseEntity<AccountDto> getAccountByEmail(@RequestParam String email){
        return ResponseEntity.ok(accountService.getAccountByEmail(email));

    }
}
