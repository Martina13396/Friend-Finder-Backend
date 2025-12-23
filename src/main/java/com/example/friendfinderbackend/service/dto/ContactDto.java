package com.example.friendfinderbackend.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

    private Long id;

    @NotBlank(message = "name.not.blank")
    private String name;
    @NotBlank(message = "email.notblank")
    private String email;
    @NotBlank(message = "phone.not.blank")
    private  String phone;
    @NotBlank(message = "message.not.blank")
    private String message;

    private Long accountId;
}
