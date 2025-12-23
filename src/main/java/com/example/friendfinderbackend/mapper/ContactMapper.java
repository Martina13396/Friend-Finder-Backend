package com.example.friendfinderbackend.mapper;

import com.example.friendfinderbackend.model.Contact;
import com.example.friendfinderbackend.service.dto.ContactDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactMapper CONTACT_MAPPER = Mappers.getMapper(ContactMapper.class);

    @Mapping(target = "account.id" , source = "accountId")
    Contact toContact(ContactDto contactDto);
    @Mapping(target = "accountId" , source = "account.id")
    ContactDto toContactDto(Contact contact);
}
