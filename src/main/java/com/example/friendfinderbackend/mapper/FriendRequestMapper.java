package com.example.friendfinderbackend.mapper;

import com.example.friendfinderbackend.model.FriendRequest;
import com.example.friendfinderbackend.service.dto.FriendRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FriendRequestMapper {
    FriendRequestMapper FRIEND_REQUEST_MAPPER = Mappers.getMapper(FriendRequestMapper.class);


    @Mapping(target = "accountEmail" , source = "sender.email")
    FriendRequestDto toFriendRequestDto(FriendRequest friendRequest);

    FriendRequest toFriendRequest(FriendRequestDto friendRequestDto);

    List<FriendRequestDto> toFriendRequestDtos(List<FriendRequest> friendRequests);

    List<FriendRequest> toFriendRequests(List<FriendRequestDto> friendRequestDtos);
}
