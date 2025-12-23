package com.example.friendfinderbackend.controller;

import com.example.friendfinderbackend.mapper.ContactMapper;
import com.example.friendfinderbackend.service.ContactService;
import com.example.friendfinderbackend.service.dto.ContactDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }


    @PostMapping("/save")
    ResponseEntity<ContactDto> save(@RequestBody @Valid ContactDto contactDto) {
        return ResponseEntity.ok(contactService.save(contactDto));
    }
}
