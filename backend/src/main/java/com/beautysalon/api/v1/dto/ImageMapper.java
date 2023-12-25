package com.beautysalon.api.v1.dto;

import com.beautysalon.api.v1.entities.Image;

import java.io.IOException;

public class ImageMapper {

    public Image toEntity(ImageDto dto) throws IOException {
        return (dto == null)
                ? null
                : Image.builder()
                .path(dto.getPath())
                .filename(dto.getFilename())
                .bytes(dto.getFile().getBytes())
                .build();
    }
}
