package com.example.friendfinderbackend.service;

import com.example.friendfinderbackend.service.dto.PostsDto;

import java.util.List;
import java.util.Map;

public interface PostService {

    PostsDto createPost(PostsDto postsDto);

    List<PostsDto> getPostsForUserAndFriends();

    List<PostsDto> getPostsByAccountId();

   List<PostsDto> deletePost(Long postId , String viewType);

   List<Map<String,Object>> getAllMediaUrls();

   List<Map<String,Object>> getFriendAlbum(Long accountId);

   List<PostsDto> getFriendPosts(Long accountId);
}
