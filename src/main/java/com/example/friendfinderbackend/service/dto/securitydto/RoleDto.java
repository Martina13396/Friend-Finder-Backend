package com.example.friendfinderbackend.service.dto.securitydto;

import com.example.friendfinderbackend.model.security.RoleCode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;

    @NotNull(message = "role.code.notnull")
    private RoleCode code;
}
