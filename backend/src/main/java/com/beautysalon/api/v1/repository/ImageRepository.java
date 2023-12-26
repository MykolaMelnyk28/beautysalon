package com.beautysalon.api.v1.repository;

import com.beautysalon.api.v1.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);
    boolean existsByName(String name);
}
