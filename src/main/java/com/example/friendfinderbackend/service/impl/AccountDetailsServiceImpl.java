package com.example.friendfinderbackend.service.impl;

import com.example.friendfinderbackend.mapper.AccountDetailsMapper;
import com.example.friendfinderbackend.model.AccountDetails;
import com.example.friendfinderbackend.model.Favorites;
import com.example.friendfinderbackend.model.Language;
import com.example.friendfinderbackend.model.WorkExperience;
import com.example.friendfinderbackend.repo.AccountDetailsRepo;
import com.example.friendfinderbackend.service.AccountDetailsService;
import com.example.friendfinderbackend.service.dto.AccountDetailsDto;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AccountDetailsServiceImpl implements AccountDetailsService {

    private  final AccountDetailsRepo accountDetailsRepo;
    private final AccountDetailsMapper  accountDetailsMapper;


    @Autowired
    public AccountDetailsServiceImpl(AccountDetailsRepo accountDetailsRepo, AccountDetailsMapper accountDetailsMapper) {
        this.accountDetailsRepo = accountDetailsRepo;
        this.accountDetailsMapper = accountDetailsMapper;

    }

    @Override
    @Transactional
    @Cacheable(value = "accountDetails" , key = "#root.target.getCurrentUserId()")
    public AccountDetailsDto findAccountDetails() {
        Long currentAccountId = getCurrentUserId();

        Optional<AccountDetails> accountDetails = accountDetailsRepo.findAccountDetailsByAccountId(currentAccountId);
        if (!accountDetails.isPresent()) {
          return new AccountDetailsDto();
        }
        return  accountDetailsMapper.toAccountDetailsDto(accountDetails.get());
    }

    @Override
    @Transactional
    @CacheEvict(value = "accountDetails" , key = "#root.target.getCurrentUserId()")
    public AccountDetailsDto updateAccountDetails(AccountDetailsDto accountDetailsDto) {
      Long currentAccountId = getCurrentUserId();
      Optional<AccountDetails> accountDetails = accountDetailsRepo.findAccountDetailsByAccountId(currentAccountId);
      if (!accountDetails.isPresent()) {
          throw new RuntimeException("Account.details.not.found");
      }
      AccountDetails existingAccountDetails = accountDetails.get();

        existingAccountDetails.setPersonalInfo(accountDetailsDto.getPersonalInfo());
        existingAccountDetails.setLocation(accountDetailsDto.getLocation());

        if (accountDetailsDto.getWorkExperiences() != null) {
            accountDetailsDto.getWorkExperiences().forEach(workDto -> {
                if(workDto.getStartDate()!=null && workDto.getStartDate().trim().isEmpty() ){
                    workDto.setStartDate(null);
                }
                if(workDto.getEndDate()!=null && workDto.getEndDate().trim().isEmpty() ){
                    workDto.setEndDate(null);
                }

                if (workDto.getId() == null) {
                    WorkExperience netWork = accountDetailsMapper.toWorkExperienceEntity(workDto);
                    netWork.setAccountDetails(existingAccountDetails);
                    existingAccountDetails.getWorkExperiences().add(netWork);
                }else {
                    existingAccountDetails.getWorkExperiences().stream().filter(existingWork -> existingWork.getId().equals(workDto.getId())).findFirst().ifPresent(existingWork -> {
                        existingWork.setCompanyName(workDto.getCompanyName());
                        existingWork.setTitle(workDto.getTitle());
                        existingWork.setCompanyLogo(workDto.getCompanyLogo());
                        existingWork.setStartDate(
                                (workDto.getStartDate()!=null && ! workDto.getStartDate().trim().isEmpty())
                                ?LocalDate.parse(workDto.getStartDate()):null
                        );

                        existingWork.setEndDate(
                                (workDto.isPresent())?null:
                                        (workDto.getEndDate()!=null&& ! workDto.getEndDate().trim().isEmpty())
                                ? LocalDate.parse(workDto.getEndDate()):null
                        );
                        existingWork.setPresent(workDto.isPresent());


                    });


                }
            });
        }
        if (accountDetailsDto.getFavorites() != null) {
            accountDetailsDto.getFavorites().forEach(favDto -> {
               if (favDto.getId() == null) {
                   Favorites favorites = accountDetailsMapper.toFavoriteEntity(favDto);
                   favorites.setAccountDetails(existingAccountDetails);
                   existingAccountDetails.getFavorites().add(favorites);
               }else {
                   existingAccountDetails.getFavorites().stream().filter(exitingFav->
                           exitingFav.equals(favDto.getId())).findFirst().ifPresent(exitingFav -> {
                       exitingFav.setFavorite(favDto.getFavorite());
                   });
               }
            });
        }

       if(accountDetailsDto.getLanguages() != null) {
           accountDetailsDto.getLanguages().forEach(langDto -> {
               if (langDto.getId() == null) {
                   Language language = accountDetailsMapper.toLanguageEntity(langDto);
                   language.setAccountDetails(existingAccountDetails);
                   existingAccountDetails.getLanguages().add(language);
               }else {
                   existingAccountDetails.getLanguages().stream().filter(exitingLang->exitingLang.getId().equals(langDto.getId()))
                           .findFirst().ifPresent(exitingLang -> {
                               exitingLang.setLanguage(langDto.getLanguage());
                               exitingLang.setLevel(langDto.getLevel());
                           });

               }
           });
       }

        AccountDetails savedAccountDetails = accountDetailsRepo.save(existingAccountDetails);
        return accountDetailsMapper.toAccountDetailsDto(savedAccountDetails);

    }

    @Override
    @Cacheable(value = "accountDetails" , key = "#accountId")
    public AccountDetailsDto getFriendAccountDetails(Long accountId) {
       Optional<AccountDetails> accountDetails= accountDetailsRepo.findAccountDetailsByAccountId(accountId);
       if (!accountDetails.isPresent()) {
           return new AccountDetailsDto();
       }
       return accountDetailsMapper.toAccountDetailsDto(accountDetails.get());
    }


    public Long getCurrentUserId() {
        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountDto.getId();
    }

}
