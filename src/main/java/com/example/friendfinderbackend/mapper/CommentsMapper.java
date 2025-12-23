package com.example.friendfinderbackend.mapper;

import com.example.friendfinderbackend.model.Comments;
import com.example.friendfinderbackend.service.dto.CommentsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
    CommentsMapper COMMENTS_MAPPER = Mappers.getMapper(CommentsMapper.class);

    Comments toComments(CommentsDto commentsDto);

    @Mapping(target = "postId" , source = "comments.post.id")
    @Mapping(target = "accountId" , source = "comments.account.id")
    @Mapping(target = "accountName" , source = "comments.account.name")
    @Mapping(target="email" , source = "comments.account.email")
    @Mapping(target = "profilePictureUrl" , source = "comments.account.profilePictureUrl")
    CommentsDto toCommentsDto(Comments comments);

    List<Comments> toComments(List<CommentsDto> commentsDtos);

    List<CommentsDto> toCommentsDtos(List<Comments> comments);
}
