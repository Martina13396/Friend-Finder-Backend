package com.example.friendfinderbackend.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionDto {

    private Long id;

    private String  action;

    private String accountName;

   private String createdAt;

}
