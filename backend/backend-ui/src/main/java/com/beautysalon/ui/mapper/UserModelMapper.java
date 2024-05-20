package com.beautysalon.ui.mapper;

import com.beautysalon.api.v1.dto.ImageDto;
import com.beautysalon.api.v1.dto.mapper.ImageMapper;
import com.beautysalon.api.v1.dto.mapper.base.AbstractMapper;
import com.beautysalon.domain.entities.UserEntity;
import com.beautysalon.ui.controller.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserModelMapper extends AbstractMapper<UserEntity, UserModel> {

    private final ImageMapper imageMapper;

    public UserModelMapper(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    @Override
    protected void copyDto(UserEntity source, UserModel destination, boolean hardSet) {
        destination.setId(source.getId());
        destination.setUsername(source.getUsername());
        destination.setPassword(source.getPassword());
        destination.setImages(source.getImages().stream()
                .map(imageMapper::toDto)
                .toList());
        source.getAuthorities().forEach(destination::setRole);
        if (source.getImages() != null && !source.getImages().isEmpty()) {
            ImageDto dto = imageMapper.toDto(source.getImages().get(0));
            destination.setImageUrl(dto.getUrl());
        }
    }

    @Override
    protected void copyEntity(UserModel source, UserEntity destination, boolean hardSet) {
        destination.setId(source.getId());
        destination.setUsername(source.getUsername());
        destination.setPassword(source.getPassword());
        destination.setImages(source.getImages().stream()
                .map(imageMapper::toEntity)
                .toList());
        destination.setAuthorities(source.getRoles());
    }
}
