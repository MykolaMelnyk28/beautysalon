package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.Image;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public interface ImageService {

    Image store(MultipartFile file, String path) throws IOException;
    Image store(Image image, String path) throws IOException;
    String load(String path);

    Image get(String filename);
    List<Image> getAll(String path);
    void move(String fromPath, String toPath);
    void delete(String path);
    boolean exists(String path);

    String createImageUrl(Image image, boolean checkOnExists);
}
