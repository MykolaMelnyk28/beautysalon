package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.Image;
import com.beautysalon.api.v1.exceptions.ResourceAlreadyExists;
import com.beautysalon.api.v1.exceptions.ResourceNotFoundException;
import com.beautysalon.api.v1.repository.ImageRepository;
import com.beautysalon.api.v1.utils.PathUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ApiProperties apiProp;

    public ImageServiceImpl(
            ImageRepository imageRepository,
            ApiProperties apiProp
    ) {
        this.imageRepository = imageRepository;
        this.apiProp = apiProp;
    }

    @Override
    public Image store(MultipartFile file, String path)
            throws ResourceAlreadyExists, IOException {
        throwIfExists(path);

        String oPath = PathUtils.originalPath(path);
        String[] parts = PathUtils.splitPathFilename(oPath);

        Image img = new Image();
        img.setPath(parts[0]);
        img.setName(parts[1]);
        img.setFullName(oPath);
        img.setOriginalFileName(file.getOriginalFilename());
        img.setSize(file.getSize());
        img.setContentType(file.getContentType());
        img.setBytes(file.getBytes());
        img.setContentType(file.getContentType());

        Image saved = imageRepository.save(img);

        // load(img.getName())

        return saved;
    }

    @Override
    public Image store(Image image, String path) throws IOException {
        throwIfExists(path);

        String[] parts = PathUtils.splitPathFilename(path);

        image.setPath(parts[0]);
        image.setName(parts[1]);
        image.setFullName(path);

        Image saved = imageRepository.save(image);

        //load(saved.getName());

        return image;
    }

    @Override
    public String load(String path) throws ResourceNotFoundException {
        Image img = getOrThrow(path);
        String url = apiProp.getBaseUrl() + "/images";
        return String.join("/", url, img.getName());
    }

    @Override
    public void delete(String path) {
        Image img = getOrThrow(path);
        imageRepository.delete(img);
    }

    @Override
    public Image get(String filename) {
        Image image = getOrThrow(filename);
        return image;
    }

    @Override
    public List<Image> getAll(String path) {
        String oPath = PathUtils.originalPath(path);
        List<Image> images = imageRepository.findAll();
        return images.stream()
                .filter(x -> x.getPath().equals(oPath))
                .toList();
    }

    @Override
    public void move(String fromPath, String toPath)
            throws ResourceNotFoundException, ResourceAlreadyExists {
        Image source = getOrThrow(fromPath);
        throwIfExists(toPath);
        String[] parts = PathUtils.splitPathFilename(toPath);

        source.setPath(parts[0]);
        source.setFullName(toPath);
        imageRepository.save(source);
    }

    private void throwIfExists(String path) throws ResourceAlreadyExists {
        if (imageRepository.existsByFullName(path)) {
            throw new ResourceAlreadyExists("Image already exists.");
        }
    }

    private void throwIfNotExists(String path) throws ResourceNotFoundException {
        if (!imageRepository.existsByFullName(path)) {
            throw new ResourceAlreadyExists("Image not found.");
        }
    }

    private Image getOrThrow(String path) throws ResourceNotFoundException {
        Image img = imageRepository.findByFullName(path)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found."));
        return img;
    }

    @Override
    public boolean exists(String path) {
        return imageRepository.existsByFullName(path);
    }

    @Override
    public String createImageUrl(Image image, boolean checkOnExists) throws ResourceNotFoundException {
        Image img = image;
        if (checkOnExists) {
            img = getOrThrow(image.getPath());
        }
        String path = PathUtils.normalizeForUrl(img.getFullName());
        return String.format("%s/%s", apiProp.getBaseUrl(), path);
    }
}
