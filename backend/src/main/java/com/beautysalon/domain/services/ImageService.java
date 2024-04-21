package com.beautysalon.domain.services;

import com.beautysalon.domain.entities.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
