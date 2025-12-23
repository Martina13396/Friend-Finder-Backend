package com.example.friendfinderbackend.controller.vm.securitycontroller;

import com.example.friendfinderbackend.controller.vm.securityvm.AuthRequestVm;
import com.example.friendfinderbackend.controller.vm.securityvm.AuthResponseVm;
import com.example.friendfinderbackend.service.securityservice.AuthService;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
//@CrossOrigin("http://localhost:4200")
public class AuthController {

   private AuthService authService;

   @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    ResponseEntity<AuthResponseVm> login (@RequestBody @Valid AuthRequestVm authRequestVm){
        return  ResponseEntity.ok(authService.login(authRequestVm));

    }

    @PostMapping("/sign-up")
    ResponseEntity<AuthResponseVm> signup (@RequestBody @Valid AccountDto accountDto) throws SystemException {
        return ResponseEntity.ok(authService.signup(accountDto));
    }
}
