package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.Image;
import com.beautysalon.api.v1.exceptions.ResourceAlreadyExists;
import com.beautysalon.api.v1.exceptions.ResourceNotFoundException;
import com.beautysalon.api.v1.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
@Transactional
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
    public String store(MultipartFile file, Path path)
            throws ResourceAlreadyExists, IOException {
        throwIfExists(path);

        Image img = new Image();
        img.setName(path.toString());
        img.setOriginalFileName(file.getOriginalFilename());
        img.setSize(file.getSize());
        img.setContentType(file.getContentType());
        img.setBytes(file.getBytes());
        img.setContentType(file.getContentType());

        imageRepository.save(img);

        return load(Path.of(img.getName()));
    }

    @Override
    public String load(Path path) throws ResourceNotFoundException {
        Image img = getOrThrow(path);
        String url = apiProp.getBaseUrl() + "/images";
        return String.join("\\", url, img.getName());
    }

    @Override
    public void delete(Path path) {
        Image img = getOrThrow(path);
        imageRepository.delete(img);
    }

    @Override
    public Image get(String filename) {
        Image image = getOrThrow(Path.of(filename));
        return image;
    }

    @Override
    public List<Image> getAll(String path) {
        return imageRepository.findAll().stream()
                .filter(x -> x.getName().startsWith(path))
                .toList();
    }

    @Override
    public void move(Path fromPath, Path toPath)
            throws ResourceNotFoundException, ResourceAlreadyExists {
        Image source = getOrThrow(fromPath);
        throwIfExists(toPath);
        source.setName(toPath.toString());
        imageRepository.save(source);
    }

    private void throwIfExists(Path path) throws ResourceAlreadyExists {
        if (imageRepository.existsByName(path.toString())) {
            throw new ResourceAlreadyExists("Image already exists.");
        }
    }

    private Image getOrThrow(Path path) throws ResourceNotFoundException {
        Image img = imageRepository.findByName(path.toString())
                .orElseThrow(() -> new ResourceNotFoundException("Image not found."));
        return img;
    }

    private String[] extractPathFilename(Path path) {
        Path parent = path.getParent();
        String pathFile = (parent != null) ? parent.toString() : "";
        String filename = path.toFile().getName();
        return new String[] {pathFile, filename};
    }
}
