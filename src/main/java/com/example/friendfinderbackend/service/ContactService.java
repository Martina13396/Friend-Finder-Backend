package com.example.friendfinderbackend.service;

import com.example.friendfinderbackend.service.dto.ContactDto;
import org.springframework.stereotype.Service;


public interface ContactService {
    ContactDto save(ContactDto contactDto);
}
