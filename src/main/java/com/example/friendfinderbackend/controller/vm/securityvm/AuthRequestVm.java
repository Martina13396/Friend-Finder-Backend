package com.example.friendfinderbackend.controller.vm.securityvm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestVm {
   private String email;
   private String password;
}
