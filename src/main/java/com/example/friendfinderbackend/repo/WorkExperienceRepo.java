package com.example.friendfinderbackend.repo;

import com.example.friendfinderbackend.model.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceRepo extends JpaRepository<WorkExperience, Long> {
}
