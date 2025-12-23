package com.example.friendfinderbackend.mapper;

import com.example.friendfinderbackend.model.Action;
import com.example.friendfinderbackend.service.dto.ActionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActionMapper {

    ActionMapper ACTION_MAPPER = Mappers.getMapper(ActionMapper.class);


    @Mapping(target = "account.name" , source = "accountName")
    Action toAction(ActionDto actionDto);

    @Mapping(target = "accountName" , source = "account.name")
    ActionDto toActionDto(Action action);

    List<Action> toActions(List<ActionDto> actionDtos);

    List<ActionDto> toActionDtos(List<Action> actions);
}
