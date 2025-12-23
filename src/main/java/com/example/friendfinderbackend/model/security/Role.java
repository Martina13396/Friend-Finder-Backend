package com.example.friendfinderbackend.model.security;

import com.example.friendfinderbackend.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Role_friendFinder")
public class Role extends BaseEntity {


    @Enumerated(EnumType.STRING)
    private RoleCode code;

    @ManyToMany(mappedBy = "roles" )
    List<Account> accounts;



}
