package com.example.friendfinderbackend.service.securityservice;

import com.example.friendfinderbackend.controller.vm.securityvm.AuthRequestVm;
import com.example.friendfinderbackend.controller.vm.securityvm.AuthResponseVm;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;

public interface AuthService {
AuthResponseVm login(AuthRequestVm authRequestVm);
AuthResponseVm signup(AccountDto accountDto);
}
