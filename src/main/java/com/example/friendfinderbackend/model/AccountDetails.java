package com.example.friendfinderbackend.model;

import com.example.friendfinderbackend.model.security.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Account_Details_Friend_Finder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetails extends BaseEntity{
    @Column(name = "PERSONAL_INFO")
    private String personalInfo;

    private String location;

    @OneToMany(mappedBy = "accountDetails",cascade = CascadeType.ALL)
    private List<WorkExperience> workExperiences = new ArrayList<>();

    @OneToMany(mappedBy = "accountDetails", cascade = CascadeType.ALL)
   private List<Language> languages = new ArrayList<>();

    @OneToMany(mappedBy = "accountDetails",cascade = CascadeType.ALL)
    private List<Favorites> favorites = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
