package com.example.friendfinderbackend.repo;

import com.example.friendfinderbackend.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepo extends JpaRepository<Comments, Long> {


    List<Comments> findByPostIdOrderByCreatedAtDesc(Long postId);

    List<Comments> findByTextContainingIgnoreCaseOrderByCreatedAtDesc(String text);
}
