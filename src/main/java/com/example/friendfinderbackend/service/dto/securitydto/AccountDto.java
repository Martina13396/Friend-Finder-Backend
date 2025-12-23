package com.example.friendfinderbackend.service.dto.securitydto;

import com.example.friendfinderbackend.service.dto.FriendRequestDto;
import com.example.friendfinderbackend.service.dto.PostsDto;
import jakarta.validation.constraints.*;
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
public class AccountDto {
    private Long id;
    @NotBlank(message = "username.notblank")
    @Size(min = 2 , message = "username.size")
    private String name;
    @NotBlank(message = "password.notblank")
    @Size(min = 7, message = "password.size")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "password.pattern"
    )
    private String password;
    @NotBlank(message = "email.notblank")
    @Email(message = "email.invalid")
    private String email;
    private boolean enabled = true;
    private String job;
//    @NotBlank(message = "profile.picture.notBlank")
    private String profilePictureUrl;
//    @NotBlank(message = "background.picture.notBlank")
    private String backGroundPictureUrl;
    @NotNull(message = "followers.count.notNull")
    private Integer followersCount = 0;


    private List<RoleDto> roles = new ArrayList<>();






}
