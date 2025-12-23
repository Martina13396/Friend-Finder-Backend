package com.example.friendfinderbackend.mapper;

import com.example.friendfinderbackend.model.AccountDetails;
import com.example.friendfinderbackend.model.Favorites;
import com.example.friendfinderbackend.model.Language;
import com.example.friendfinderbackend.model.WorkExperience;
import com.example.friendfinderbackend.service.dto.AccountDetailsDto;
import com.example.friendfinderbackend.service.dto.FavoritesDto;
import com.example.friendfinderbackend.service.dto.LanguageDto;
import com.example.friendfinderbackend.service.dto.WorkExperienceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountDetailsMapper {
    AccountDetailsMapper ACCOUNT_DETAILS_MAPPER = Mappers.getMapper(AccountDetailsMapper.class);
    @Mapping(source = "personalInfo", target = "personalInfo")
    AccountDetailsDto toAccountDetailsDto(AccountDetails accountDetails);

    AccountDetails toAccountDetails(AccountDetailsDto accountDetailsDto);

    List<AccountDetailsDto> toAccountDetailsDto(List<AccountDetails> accountDetails);

    List<AccountDetails> toAccountDetails(List<AccountDetailsDto> accountDetailsDtos);

    WorkExperience toWorkExperienceEntity(WorkExperienceDto dto);
    Favorites toFavoriteEntity(FavoritesDto dto);
    Language toLanguageEntity(LanguageDto dto);
}
