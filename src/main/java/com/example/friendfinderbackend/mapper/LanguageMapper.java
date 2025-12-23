package com.example.friendfinderbackend.mapper;

import com.example.friendfinderbackend.model.Language;
import com.example.friendfinderbackend.service.dto.LanguageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
    LanguageMapper LANGUAGE_MAPPER = Mappers.getMapper(LanguageMapper.class);

    Language toLanguage(LanguageDto languageDto);
    LanguageDto toLanguageDto(Language language);
}
