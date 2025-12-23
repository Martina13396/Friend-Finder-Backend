package com.example.friendfinderbackend.repo;

import com.example.friendfinderbackend.model.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesRepo extends JpaRepository<Favorites, Long> {
}
