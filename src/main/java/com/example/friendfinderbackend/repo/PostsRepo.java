package com.example.friendfinderbackend.repo;

import com.example.friendfinderbackend.model.Posts;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepo extends JpaRepository<Posts, Long> {

    List<Posts> findAllByAccountId(Long AccountId);

    @Query(
    """
SELECT p FROM Posts p
    WHERE (p.account.id = :accountId
    OR p.account.id IN :friendIds)
    AND p.deleted  = false
    ORDER BY p.createdAt DESC
"""
    )
    List<Posts> findAllByUserAndFriendsAndDeletedIsFalse(@Param("accountId") Long AccountId , @Param("friendIds") List<Long> FriendIds  );


    List<Posts> findAllByAccountIdAndDeletedIsFalseOrderByCreatedAtDesc(Long accountId );

    @Query(value = "SELECT p.media_url AS url, p.media_type AS type FROM POST p WHERE p.account_id = :accountId AND p.media_url IS NOT NULL " +
            "UNION " +
            "SELECT ac.profile_picture_url AS url, 'IMAGE' AS type FROM ACCOUNT_FRIEND_FINDER ac WHERE ac.id = :accountId AND ac.profile_picture_url IS NOT NULL " +
            "UNION " +
            "SELECT ac.back_ground_picture_url AS url, 'IMAGE' AS type FROM ACCOUNT_FRIEND_FINDER ac WHERE ac.id = :accountId AND ac.back_ground_picture_url IS NOT NULL",
            nativeQuery = true)
    List<Object[]> findAlbumMediaByAccountId(Long accountId);

    @Query("SELECT p FROM Posts p WHERE " +
            "LOWER(CAST(p.content AS string)) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.mediaUrl) LIKE LOWER(CONCAT('%', :query, '%'))" + "and p.deleted = false" + " order by p.createdAt desc ")

    List<Posts> searchPosts (@Param("query") String query);
}
