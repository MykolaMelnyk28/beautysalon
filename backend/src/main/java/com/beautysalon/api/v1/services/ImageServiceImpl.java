package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.Image;
import com.beautysalon.api.v1.exceptions.ResourceAlreadyExists;
import com.beautysalon.api.v1.exceptions.ResourceNotFoundException;
import com.beautysalon.api.v1.exceptions.StorageFileNotFoundException;
import com.beautysalon.api.v1.repository.ImageRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.print.DocFlavor;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    private final String BASE_IMAGES_URL = "http://localhost:8080/api/v1/images";

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public String store(MultipartFile file, Path path)
            throws ResourceAlreadyExists, IOException {
        String[] fileData = extractPathFilename(path);

        throwIfExists(path);

        Image img = Image.builder()
                .path(fileData[0])
                .filename(file.getOriginalFilename())
                .bytes(file.getBytes())
                .build();

        imageRepository.save(img);

        return load(Path.of(img.getFullName()));
    }

    @Override
    public String load(Path path) throws ResourceNotFoundException {
        Image img = getOrThrow(path);
        String pathFile = String.join("/", BASE_IMAGES_URL, img.getFullName().toString());
        return pathFile;
    }

    @Override
    public Resource loadAsResource(Path path) {
        try {
            String file = load(path);
            Resource resource = new UrlResource(URI.create(file));
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new ResourceNotFoundException(
                        "Could not read file: " + path.toFile().getName());
            }
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException("Could not read file: " + path.toFile().getName(), e);
        }
    }

    @Override
    public List<String> loadAll() {
        return imageRepository.findAll().stream()
                .map(Image::getFullName)
                .toList();
    }

    @Override
    public List<String> loadAllByPath(String path) {
        return imageRepository.findAllByPath(path).stream()
                .map(Image::getFullName)
                .toList();
    }

    @Override
    public void delete(Path path) {
        Image img = getOrThrow(path);
        imageRepository.delete(img);
    }

    @Override
    public void move(Path fromPath, Path toPath)
            throws ResourceNotFoundException, ResourceAlreadyExists {
        Image source = getOrThrow(fromPath);
        throwIfExists(toPath);
        source.setPath(toPath.getParent().toString());
        source.setFilename(toPath.toFile().getName());
        imageRepository.save(source);
    }

    private void throwIfExists(Path path) throws ResourceAlreadyExists {
        String[] pathData = extractPathFilename(path);
        if (imageRepository.existsByPathAndFilename(pathData[0], pathData[1])) {
            throw new ResourceAlreadyExists("Image already exists.");
        }
    }

    private Image getOrThrow(Path path) throws ResourceNotFoundException {
        String[] fileData = extractPathFilename(path);
        Image img = imageRepository.findByPathAndFilename(fileData[0], fileData[1])
                .orElseThrow(() -> new ResourceNotFoundException("Image not found."));
        return img;
    }

    private String[] extractPathFilename(Path path) {
        String pathFile = path.getParent().toString();
        String filename = path.toFile().getName();
        return new String[] {pathFile, filename};
    }
}
