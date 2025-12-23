package com.example.friendfinderbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LANGUAGE_FRIEND_FINDER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Language extends BaseEntity{
    @Column(name = "LANG_NAME")
    private String language;
    @Column(name = "LANG_LEVEL")
    private String level;

    @ManyToOne
    @JoinColumn(name = "account_details_id")
    private AccountDetails accountDetails;
}
