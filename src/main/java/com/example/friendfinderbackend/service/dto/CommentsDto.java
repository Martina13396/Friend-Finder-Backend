package com.example.friendfinderbackend.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
    private Long id;

    @NotBlank(message = "comment.not.Blank")
    private String text;
    private Long postId;
    private Long accountId;
    private String profilePictureUrl;
    private String accountName;
    private String createdAt;

    private String email;
}
