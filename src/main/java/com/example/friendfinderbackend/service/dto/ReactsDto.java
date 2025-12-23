package com.example.friendfinderbackend.service.dto;

import com.example.friendfinderbackend.model.ReactType;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReactsDto {

    private Long id;

    private ReactType reactType;

    private Long accountId;

    private Long postId;
}
