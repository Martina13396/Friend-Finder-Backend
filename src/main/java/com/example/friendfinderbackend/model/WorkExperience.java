package com.example.friendfinderbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperience extends BaseEntity{

    private String companyName;
    private String title;
    private String companyLogo;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean present;

    @ManyToOne
    private AccountDetails accountDetails;
}
