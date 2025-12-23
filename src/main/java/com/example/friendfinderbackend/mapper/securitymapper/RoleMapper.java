package com.example.friendfinderbackend.mapper.securitymapper;

import com.example.friendfinderbackend.model.security.Role;
import com.example.friendfinderbackend.service.dto.securitydto.RoleDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toRoleDto(Role role);
    Role toRole(RoleDto roleDto);
    List<RoleDto> toRoleDtos(List<Role> roles);
    List<Role> toRoles(List<RoleDto> roleDtos);
}
