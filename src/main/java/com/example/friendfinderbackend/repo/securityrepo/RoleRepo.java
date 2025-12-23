package com.example.friendfinderbackend.repo.securityrepo;

import com.example.friendfinderbackend.model.security.Role;
import com.example.friendfinderbackend.model.security.RoleCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByCode(RoleCode code);
}
