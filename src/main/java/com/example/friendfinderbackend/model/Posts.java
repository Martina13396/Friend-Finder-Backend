package com.example.friendfinderbackend.model;

import com.example.friendfinderbackend.model.security.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Handler;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "POST")
public class Posts extends BaseEntity {


    @Lob
    @Column(name = "CONTENT", columnDefinition = "CLOB")
    private String content;

    private String mediaUrl;

    private String mediaType;

    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @OneToMany(mappedBy = "post" , fetch = FetchType.EAGER)
    private Set<Comments> comments =  new HashSet<>();

    @OneToMany(mappedBy = "post" , fetch = FetchType.EAGER)
    private Set<Reacts> reacts =  new HashSet<>();

}
