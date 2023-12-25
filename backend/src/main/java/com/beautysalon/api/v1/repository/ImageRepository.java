package com.beautysalon.api.v1.repository;

import com.beautysalon.api.v1.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    Optional<Image> findByPathAndFilename(String path, String filename);
    List<Image> findAllByPath(String path);
    boolean existsByPathAndFilename(String path, String filename);
}
