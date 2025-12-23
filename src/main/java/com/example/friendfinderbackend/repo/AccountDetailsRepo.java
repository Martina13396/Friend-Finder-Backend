package com.example.friendfinderbackend.repo;

import com.example.friendfinderbackend.model.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDetailsRepo extends JpaRepository<AccountDetails, String> {

    Optional<AccountDetails> findAccountDetailsByAccountId(Long accountId);
}
