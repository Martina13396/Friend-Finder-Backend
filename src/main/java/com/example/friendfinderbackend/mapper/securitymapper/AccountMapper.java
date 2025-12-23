package com.example.friendfinderbackend.mapper.securitymapper;

import com.example.friendfinderbackend.controller.vm.securityvm.AuthResponseVm;
import com.example.friendfinderbackend.model.security.Account;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper ACCOUNT_MAPPER = Mappers.getMapper( AccountMapper.class );
    @Mappings({


            @Mapping(target = "roles", ignore = true),

            @Mapping(target = "profilePictureUrl", source = "profilePictureUrl"),
            @Mapping(target = "backGroundPictureUrl", source = "backGroundPictureUrl"),
            @Mapping(target = "job", source = "job"),
            @Mapping(target = "followersCount", source = "followersCount")
    })
    AccountDto toAccountDto(Account account);
    Account toAccount(AccountDto accountDto);

    List<AccountDto> toAccountDtos(List<Account> accounts);
    List<Account> toAccounts(List<AccountDto> accountDtos);
    AuthResponseVm toAuthResponseVm(AccountDto accountDto);
}
