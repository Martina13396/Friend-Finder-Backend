package com.example.friendfinderbackend.controller;

import com.example.friendfinderbackend.controller.vm.SearchResultVm;
import com.example.friendfinderbackend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/global")
    ResponseEntity<SearchResultVm> searchEveryThing(@RequestParam String query) {
        return ResponseEntity.ok(searchService.searchEveryThing(query));
    }
}
