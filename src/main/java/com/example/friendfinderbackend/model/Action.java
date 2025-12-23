package com.example.friendfinderbackend.model;

import com.example.friendfinderbackend.model.security.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
public class Action extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private String  action;



}
