package com.example.friendfinderbackend.repo;

import com.example.friendfinderbackend.model.FriendRequest;
import com.example.friendfinderbackend.model.FriendRequestStatus;
import com.example.friendfinderbackend.model.security.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FriendRequestRepo extends JpaRepository<FriendRequest, Long> {

    List<FriendRequest> findAllBySenderIdOrReceiverId(Long senderId , Long receiverId);



    @Query("""
 SELECT
            CASE
                WHEN f.sender.id = :accountId THEN f.receiver.id
                ELSE f.sender.id
            END
        FROM FriendRequest f
        WHERE (f.sender.id = :accountId OR f.receiver.id = :accountId)
        AND f.status = 'ACCEPTED'
"""

    )
    List<Long> findAcceptedFriendsId(Long accountId);

    List<FriendRequest> findAllByReceiverIdAndStatus(Long receiverId, FriendRequestStatus status);

  Long countBySenderIdAndStatusAndReadIsFalse(Long senderId, FriendRequestStatus status);

    boolean existsBySenderAndReceiver(Account sender , Account receiver);

    @Modifying
    @Transactional
    @Query("UPDATE FriendRequest f SET f.read = true " +
            "WHERE ((f.receiver.id = :accountId) OR (f.sender.id = :accountId AND f.status = 'ACCEPTED')) " +
            "AND f.read = false ")
    void markRead(long accountId);

List<FriendRequest>findAllBySenderIdAndStatus(Long senderId, FriendRequestStatus status);

Long countByReceiverIdAndStatusAndReadIsFalse(Long receiverId, FriendRequestStatus status);
}
