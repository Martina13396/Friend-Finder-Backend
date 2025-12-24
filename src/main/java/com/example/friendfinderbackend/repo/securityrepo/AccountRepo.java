package com.example.friendfinderbackend.repo.securityrepo;

import com.example.friendfinderbackend.model.security.Account;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

    Optional<Account> getAccountByEmailIgnoreCase(String Email);

    @Query("""
SELECT a from Account a where a.id <> :accountId
AND a.id NOT IN (
            SELECT
                CASE
                    WHEN fr.sender.id = :accountId THEN fr.receiver.id
                    ELSE fr.sender.id
                END
            FROM FriendRequest fr
            WHERE (fr.sender.id = :accountId OR fr.receiver.id = :accountId)
)
""")

    List<Account> findAccountsNotFriendsOrRequested(@Param("accountId") Long accountId);

    @Query("""
    SELECT a FROM Account a
    WHERE a.id <> :accountId
    AND LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%'))
   
    AND a.id NOT IN (
            SELECT
                CASE
                    WHEN fr.sender.id = :accountId THEN fr.receiver.id
                    ELSE fr.sender.id
                END
            FROM FriendRequest fr
            WHERE (fr.sender.id = :accountId OR fr.receiver.id = :accountId)
    )
""")

    List<Account> searchNewPeople(@Param("query") String query , @Param("accountId")Long accountId);

}
