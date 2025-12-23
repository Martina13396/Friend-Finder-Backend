package com.example.friendfinderbackend.service.dto;

import com.example.friendfinderbackend.model.Favorites;
import com.example.friendfinderbackend.model.security.Account;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsDto {
    private Long id;
    private String personalInfo;
    private String location;

    private List<WorkExperienceDto> workExperiences;

    private List<FavoritesDto> favorites;

    private List<LanguageDto> languages;


}
