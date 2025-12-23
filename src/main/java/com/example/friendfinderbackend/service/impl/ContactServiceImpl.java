package com.example.friendfinderbackend.service.impl;

import com.example.friendfinderbackend.mapper.ContactMapper;
import com.example.friendfinderbackend.model.Contact;
import com.example.friendfinderbackend.repo.ContactRepo;
import com.example.friendfinderbackend.service.ContactService;
import com.example.friendfinderbackend.service.dto.ContactDto;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {
   private ContactMapper contactMapper;
   private ContactRepo  contactRepo;

   @Autowired
    public ContactServiceImpl(ContactMapper contactMapper, ContactRepo contactRepo) {
        this.contactMapper = contactMapper;
        this.contactRepo = contactRepo;
    }

    @Override
    public ContactDto save(ContactDto contactDto) {
        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long accountId = accountDto.getId();
        contactDto.setAccountId(accountId);
      Contact contact = contactMapper.toContact(contactDto);
      if (contact.getId() != null) {
          throw new RuntimeException("id.must.null");
      }

    Contact savedContact = contactRepo.save(contact);
      return contactMapper.toContactDto(savedContact);

    }
}
