package com.beautysalon.core.services;

import com.beautysalon.core.repository.AdministratorRepository;
import com.beautysalon.core.entities.Administrator;
import com.beautysalon.core.entities.Image;
import com.beautysalon.core.exceptions.ResourceNotFoundException;
import com.beautysalon.core.exceptions.StorageException;
import com.beautysalon.core.utils.PathUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {
    private final AdministratorRepository administratorRepository;
    private final ImageService imageService;

    public AdministratorService(
            AdministratorRepository administratorRepository,
            ImageService imageService
    ) {
        this.administratorRepository = administratorRepository;
        this.imageService = imageService;
    }

    public Administrator create(Administrator admin) {
        Administrator saved = administratorRepository.save(admin);
        return saved;
    }

    public List<Administrator> getAll() {
        return administratorRepository.findAll();
    }

    public Optional<Administrator> getById(Long id) {
        return administratorRepository.findById(id);
    }

    public Image putPreviewImage(String username, Image image) {
        String path = PathUtils.parseEmployeeImagePath(username, image.getName());
        if (imageService.exists(path)) {
            imageService.delete(path);
        }
        image.setPreviewImage(true);
        return putImage(username, image, image.getName());
    }

    public Image putImage(String username, Image image) {
        return putImage(username, image, image.getName());
    }

    public Image putImage(String username, Image image, String filename) {
        try {
            String oPath = PathUtils.originalPath(filename);
            Administrator admin = getByUsernameOrThrow(username);
            String fullName = PathUtils.parseEmployeeImagePath(username, oPath);
            image.setUser(admin.getUser());
            return imageService.store(image, fullName);
        } catch (Exception e) {
            throw new StorageException("Error store image for employee.");
        }
    }

    public List<Image> getImages(String username) {
        Administrator admin = getByUsernameOrThrow(username);
        return imageService.getAll(PathUtils.parseEmployeeImagePath(username, ""));
    }

    public void deleteImage(String username, String filename) {
        Administrator admin = getByUsernameOrThrow(username);
        imageService.delete(PathUtils.parseEmployeeImagePath(username, filename));
    }

    public Optional<Image> getPreviewImage(String username) {
        List<Image> images = getImages(username);
        if (images.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(images.get(0));
    }

    public Optional<Administrator> getByUsername(String username) {
        return administratorRepository.findByUsername(username);
    }

    public void deleteByUsername(String username) {
        Administrator administrator = getByUsernameOrThrow(username);
        administratorRepository.deleteById(administrator.getId());
    }

    Administrator getByUsernameOrThrow(String username) {
        return getByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("Administrator not found."));
    }
}
