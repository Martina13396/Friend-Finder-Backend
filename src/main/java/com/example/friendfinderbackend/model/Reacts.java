package com.example.friendfinderbackend.model;

import com.example.friendfinderbackend.model.security.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"account_id" , "post_id"})
        }
)
public class Reacts extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private ReactType reactType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id" )
    private Account account;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts post;
}
