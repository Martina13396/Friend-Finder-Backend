package com.example.friendfinderbackend.mapper;

import com.example.friendfinderbackend.model.Posts;
import com.example.friendfinderbackend.model.Reacts;
import com.example.friendfinderbackend.service.dto.PostsDto;
import com.example.friendfinderbackend.service.dto.ReactsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReactsMapper {
    ReactsMapper REACTS_MAPPER = Mappers.getMapper(ReactsMapper.class);

    @Mapping(target = "postId" , source = "reacts.post.id")
    @Mapping(target = "accountId" , source = "reacts.account.id")

   ReactsDto  toReactsDto(Reacts reacts);

   Reacts toReacts(ReactsDto reactsDto);

}
