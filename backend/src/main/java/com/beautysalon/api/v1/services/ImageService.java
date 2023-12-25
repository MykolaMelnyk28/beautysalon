package com.beautysalon.api.v1.services;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public interface ImageService {

    String store(MultipartFile file, Path path) throws IOException;
    String load(Path path);
    Resource loadAsResource(Path path);
    List<String> loadAll();
    List<String> loadAllByPath(String path);
    void delete(Path path);
    void move(Path fromPath, Path toPath);

}
