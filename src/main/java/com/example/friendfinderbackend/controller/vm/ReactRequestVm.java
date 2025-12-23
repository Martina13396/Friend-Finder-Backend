package com.example.friendfinderbackend.controller.vm;

import com.example.friendfinderbackend.model.ReactType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReactRequestVm {

    private ReactType reactType;
    private Long postId;
}
