package com.example.friendfinderbackend.model;

import com.example.friendfinderbackend.model.security.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact extends BaseEntity {

    private String name;
    private String email;
    private String phone;
    private String message;

    @ManyToOne
    private Account account;


}
