package com.example.friendfinderbackend.repo;

import com.example.friendfinderbackend.model.ReactType;
import com.example.friendfinderbackend.model.Reacts;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactsRepo extends JpaRepository<Reacts, String> {


    Optional<Reacts> findByAccountIdAndPostId (long accountId, long postId);



    @Query("SELECT r.reactType, COUNT(r) FROM Reacts r WHERE r.post.id = :postId GROUP BY r.reactType")
    List<Object[]> countReactsByType(@Param("postId") Long postId);
}
