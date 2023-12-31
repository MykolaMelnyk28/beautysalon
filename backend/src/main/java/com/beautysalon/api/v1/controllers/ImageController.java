package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1.dto.ImageDto;
import com.beautysalon.api.v1.dto.mapper.ImageMapper;
import com.beautysalon.api.v1.entities.Image;
import com.beautysalon.api.v1.services.ApiProperties;
import com.beautysalon.api.v1.services.ImageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/v1/images")
public class ImageController {

    private final ImageService imageService;
    private final ImageMapper imageMapper;
    private final ApiProperties apiProp;

    public ImageController(
            ImageService imageService,
            ImageMapper imageMapper,
            ApiProperties apiProp
    ) {
        this.imageService = imageService;
        this.imageMapper = imageMapper;
        this.apiProp = apiProp;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestParam("file") MultipartFile file,
            @RequestParam("filename") String filename
    ) throws IOException {
        imageService.store(file, Path.of(filename));
        String url = String.join("/", apiProp.getBaseUrl(), "images", filename);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/{filename}")
    private ResponseEntity<?> getImageByName(
            @PathVariable String filename
    ) {
        String s = filename.replace("+", "/");
        Image image = imageService.get(s);
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }

    @GetMapping("/{path}/")
    private ResponseEntity<List<ImageDto>> getImagesByPath(
            @PathVariable String path
    ) {
        List<Image> images = imageService.getAll(path);
        return ResponseEntity.ok(images.stream()
                .map(imageMapper::toDto)
                .toList());
    }

}
