package com.example.friendfinderbackend.repo;

import com.example.friendfinderbackend.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepo extends JpaRepository<Action, Long> {

    List<Action> findTop5ByAccountIdOrderByCreatedAtDesc(Long accountId);
}
