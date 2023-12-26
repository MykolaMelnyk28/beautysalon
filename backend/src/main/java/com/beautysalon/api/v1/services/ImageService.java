package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.Image;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public interface ImageService {

    String store(MultipartFile file, Path path) throws IOException;
    String load(Path path);

    Image get(String filename);
    List<Image> getAll(String path);
    void move(Path fromPath, Path toPath);
    void delete(Path path);

}
