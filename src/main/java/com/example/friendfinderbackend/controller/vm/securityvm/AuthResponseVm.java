package com.example.friendfinderbackend.controller.vm.securityvm;

import com.example.friendfinderbackend.model.security.RoleCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseVm {
    private String token;
    private List<RoleCode> userRoles;

    private Long accountId;
}
