package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.Administrator;
import com.beautysalon.api.v1.entities.EmployeeFilter;
import com.beautysalon.api.v1.entities.Image;
import com.beautysalon.api.v1.entities.Master;
import com.beautysalon.api.v1.exceptions.ResourceNotFoundException;
import com.beautysalon.api.v1.repository.AdministratorRepository;
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

    public List<Administrator> getAll() {
        return administratorRepository.findAll();
    }

    public Optional<Administrator> getById(Long id) {
        return administratorRepository.findById(id);
    }

    public void putImage(String username, Image image) {
        putImage(username, image, image.getName());
    }

    public void putImage(String username, Image image, String filename) {
        Administrator admin = getByUsernameOrThrow(username);
        String fullName = String.format("emp/%s/%s", admin.getId().toString(), filename);
        image.setName(fullName);
        admin.getUser().getImages().add(image);
        administratorRepository.save(admin);
    }

    public List<Image> getImages(String username) {
        Administrator master = getByUsernameOrThrow(username);
        return imageService.getAll(String.format("emp/%s", master.getId().toString()));
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
