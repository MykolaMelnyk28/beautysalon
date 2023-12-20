package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1.entities.Image;
import com.beautysalon.api.v1.services.ImageService;
import com.beautysalon.api.v1.storage.FileUploadController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/v1/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("path") String path,
            @RequestParam("filename") String filename
    ) throws IOException {
        String pathFile = imageService.store(file, Path.of(path, filename));
        return ResponseEntity.ok(pathFile.toString());
    }

    @GetMapping("/{path}")
    public ResponseEntity<List<String>> getImageUrls(
            @PathVariable String path
    ) {
        List<String> list = imageService.loadAll().stream().map(
                        p -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "getImage",
                                p
                        ).build().toUri().toString()
                ).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{path}/{filename}")
    public ResponseEntity<?> getImage(
            @PathVariable("path") String path,
            @PathVariable("filename") String filename
    ) {
        Resource file = imageService.loadAsResource(Path.of(path, filename));

        if (file == null) return ResponseEntity.notFound().build();

        StringBuilder builder = new StringBuilder();
        builder.append("attachment; filename=\"")
                .append(file.getFilename())
                .append("\"");

        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                builder.toString()).body(file);
    }
}
