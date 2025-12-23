package com.example.friendfinderbackend.controller;

import com.example.friendfinderbackend.service.AccountDetailsService;
import com.example.friendfinderbackend.service.dto.AccountDetailsDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accountDetails")
public class AccountDetailsController {
    private AccountDetailsService accountDetailsService;

    @Autowired
    public AccountDetailsController(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }

    @GetMapping("/about")
    ResponseEntity<AccountDetailsDto> findAccountDetails(){
        return ResponseEntity.ok(accountDetailsService.findAccountDetails());
    }

    @PutMapping ("/updateDetails")
    ResponseEntity<AccountDetailsDto> updateAccountDetails(@RequestBody @Valid AccountDetailsDto accountDetailsDto){
        return ResponseEntity.ok(accountDetailsService.updateAccountDetails(accountDetailsDto));
    }

    @GetMapping("/getFriendDetails")
    ResponseEntity<AccountDetailsDto> getFriendAccountDetails(@RequestParam Long accountId){
        return ResponseEntity.ok(accountDetailsService.getFriendAccountDetails(accountId));
    }
}
