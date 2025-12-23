package com.example.friendfinderbackend.service;

import com.example.friendfinderbackend.service.dto.CommentsDto;

import java.util.List;

public interface CommentsService {
    CommentsDto addComment(CommentsDto commentsDto);

    List<CommentsDto> getCommentsForPost(Long postId);
    void deleteComment(Long commentId);
}
