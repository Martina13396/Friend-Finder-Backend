package com.example.friendfinderbackend.service;

import com.example.friendfinderbackend.controller.vm.ReactRequestVm;
import com.example.friendfinderbackend.model.ReactType;
import com.example.friendfinderbackend.service.dto.ReactsDto;

import java.util.Map;

public interface ReactsService {

    ReactsDto toggleReacts (ReactRequestVm reactRequestVm);

    Map<ReactType , Integer> getAllReactsCountForPost(long postId );
}
