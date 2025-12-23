package com.example.friendfinderbackend.service.dto;

import com.example.friendfinderbackend.model.Reacts;
import com.example.friendfinderbackend.model.security.Account;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostsDto {

    private Long id;

    @NotBlank(message = "post.blank")
    private String content;

    private String mediaUrl;
    @Pattern(
            regexp = "image|video",
            message = "media.type"
    )
    private String mediaType;


    private String accountProfilePictureUrl;

    private String accountName;
    private Long accountId;

    private String accountEmail;

    private String createdAt;

    private boolean deleted = false;

    private List<ReactsDto> reacts =  new ArrayList<>();

    private List<CommentsDto> comments =  new ArrayList<>();
}
