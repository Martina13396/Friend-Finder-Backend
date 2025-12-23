package com.example.friendfinderbackend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceDto {

    private Long id;
    private String companyName;
    private String title;
    private String companyLogo;
    private String startDate;
    private String endDate;
    private boolean isPresent;


}
