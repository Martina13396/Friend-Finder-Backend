package com.example.friendfinderbackend.mapper;

import com.example.friendfinderbackend.model.Posts;

import com.example.friendfinderbackend.service.dto.PostsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",  uses = { ReactsMapper.class, CommentsMapper.class })
public interface PostsMapper {

    PostsMapper POSTS_MAPPER = Mappers.getMapper(PostsMapper.class);

   @Mapping(target = "accountName" , source = "posts.account.name")
   @Mapping(target = "accountProfilePictureUrl" , source = "posts.account.profilePictureUrl")
   @Mapping(target = "accountId" , source = "posts.account.id")
   @Mapping(target = "reacts" , source = "posts.reacts")
   @Mapping(target = "comments" , source = "posts.comments")
   @Mapping(target = "mediaUrl" , source = "posts.mediaUrl")
   @Mapping(target = "mediaType" , source = "posts.mediaType")
   @Mapping(target = "accountEmail" , source = "posts.account.email")


    PostsDto toPostsDto(Posts posts);

    @Mapping(target = "mediaUrl" , source = "postsDto.mediaUrl")
    @Mapping(target = "mediaType" , source = "postsDto.mediaType")
    @Mapping(target = "id" , source = "postsDto.id")
    @Mapping(target = "content" , source = "postsDto.content")

    Posts toPosts(PostsDto postsDto);

    List<Posts> toPostsList(List<PostsDto> postsDtos);

    List<PostsDto> toPostsDtos(List<Posts> postsDtos);
}
