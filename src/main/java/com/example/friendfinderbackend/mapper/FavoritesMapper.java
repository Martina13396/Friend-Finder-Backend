package com.example.friendfinderbackend.mapper;

import com.example.friendfinderbackend.model.Favorites;
import com.example.friendfinderbackend.service.dto.FavoritesDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FavoritesMapper {
    FavoritesMapper FAVORITES_MAPPER = Mappers.getMapper(FavoritesMapper.class);

    Favorites toFavorites (FavoritesDto favoritesDto);

    FavoritesDto toFavoritesDto (Favorites favorites);
}
