package com.example.friendfinderbackend.controller.vm;

import com.example.friendfinderbackend.service.dto.CommentsDto;
import com.example.friendfinderbackend.service.dto.PostsDto;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
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
public class SearchResultVm {
    List<PostsDto> posts = new ArrayList<>();
    List<CommentsDto> comments = new ArrayList<>();
    List<AccountDto> accounts = new ArrayList<>();
}
