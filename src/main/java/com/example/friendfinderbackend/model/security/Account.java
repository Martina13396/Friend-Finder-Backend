package com.example.friendfinderbackend.model.security;

import com.example.friendfinderbackend.model.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="ACCOUNT_FRIEND_FINDER")
public class Account extends BaseEntity {


    private String name;
    private String password;
    @Column(unique = true)
    private String email;
    private boolean enabled = true;
    private String job;
    private String profilePictureUrl;
    private String backGroundPictureUrl;
    private Integer followersCount = 0;



    @ManyToMany(fetch = FetchType.EAGER )
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "account" , cascade = CascadeType.ALL , fetch = FetchType.LAZY )

    private List<Posts> posts = new ArrayList<>();


    @OneToMany(mappedBy = "account" , fetch = FetchType.LAZY)
    private List<Reacts> reacts = new ArrayList<>();

    @OneToMany(mappedBy = "account" , fetch = FetchType.LAZY)
    private List<Comments> comments = new ArrayList<>();

    @OneToMany(mappedBy = "sender" , fetch = FetchType.LAZY)
    private List<FriendRequest> sentRequests = new ArrayList<>();

    @OneToMany(mappedBy = "receiver" , fetch = FetchType.LAZY)
    private List<FriendRequest> receivedRequests = new ArrayList<>();


    @OneToMany(mappedBy = "account")
    private List<Action>  actions = new ArrayList<>();

    @OneToOne( mappedBy = "account")
    private AccountDetails accountDetails;


    @OneToMany(mappedBy = "account" , cascade ={ CascadeType.PERSIST , CascadeType.MERGE} )
    private List<Contact> contacts = new ArrayList<>();


}
