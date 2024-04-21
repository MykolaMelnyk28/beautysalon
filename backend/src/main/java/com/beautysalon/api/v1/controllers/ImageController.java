package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1.dto.ImageDto;
import com.beautysalon.api.v1.dto.mapper.ImageMapper;
import com.beautysalon.domain.entities.Image;
import com.beautysalon.domain.services.ApiProperties;
import com.beautysalon.domain.services.ImageService;
import com.beautysalon.utils.PathUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;
    private final ImageMapper imageMapper;

    public ImageController(
            ImageService imageService,
            ImageMapper imageMapper
    ) {
        this.imageService = imageService;
        this.imageMapper = imageMapper;
    }

    @PutMapping("/{filename}")
    public ResponseEntity<ImageDto> create(
            @PathVariable("filename") String filename,
            @RequestBody MultipartFile file
    ) throws IOException {
        String oFilename = PathUtils.originalPath(filename);
        boolean isFileExists = imageService.exists(oFilename);
        if (!isFileExists) {
            Image image = imageService.store(file, oFilename);
            ImageDto response = imageMapper.toDto(image);
            URI uri = URI.create(PathUtils.normalizeForUrl(response.getFullName()));
            return ResponseEntity.created(uri).body(response);
        }
        imageService.store(file, oFilename);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{filename}")
    private ResponseEntity<?> getImageByName(
            @PathVariable String filename
    ) {
        String s = filename.replace("+", "/");
        Image image = imageService.get(s);
        return ResponseEntity.ok()
                .header("File-Name", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }

    // /v1/images?g=salon+image.jpg
    @GetMapping
    private ResponseEntity<List<ImageDto>> getImagesByPath(
            @RequestParam(value = "g", defaultValue = "") String group
    ) {
        List<Image> images = imageService.getAll(group);
        List<ImageDto> dtos = images.stream()
                .map(imageMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{imageName}")
    public ResponseEntity<Void> deleteByName(
            @PathVariable String imageName
    ) {
        imageService.delete(imageName);
        return ResponseEntity.noContent().build();
    }
}
