package com.example.friendfinderbackend.mapper;

import com.example.friendfinderbackend.model.WorkExperience;
import com.example.friendfinderbackend.service.dto.WorkExperienceDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WorkExperienceMapper {

    WorkExperienceMapper WORK_EXPERIENCE_MAPPER= Mappers.getMapper(WorkExperienceMapper.class);

    WorkExperience toWorkExperience (WorkExperienceDto workExperienceDto);
    WorkExperienceDto toWorkExperienceDto (WorkExperience workExperience);
}
