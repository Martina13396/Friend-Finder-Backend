package com.example.friendfinderbackend.controller;

import com.example.friendfinderbackend.controller.vm.ReactRequestVm;
import com.example.friendfinderbackend.model.ReactType;
import com.example.friendfinderbackend.service.ReactsService;
import com.example.friendfinderbackend.service.dto.ReactsDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reacts")
public class ReactsController {

    private ReactsService  reactsService;

    @Autowired
    public ReactsController(ReactsService reactsService) {
        this.reactsService = reactsService;
    }

    @PostMapping("/toggleReact")
    ResponseEntity<ReactsDto> toggleReacts (@RequestBody @Valid ReactRequestVm reactRequestVm) {
        return ResponseEntity.ok(reactsService.toggleReacts(reactRequestVm));

    }


    @GetMapping("/count")
    ResponseEntity<Map<ReactType,Integer>> getAllReactsCountForPost (@RequestParam long postId ) {
        return ResponseEntity.ok(reactsService.getAllReactsCountForPost(postId ));

    }
}
