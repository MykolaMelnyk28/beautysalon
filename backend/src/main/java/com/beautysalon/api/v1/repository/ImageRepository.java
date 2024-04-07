package com.beautysalon.api.v1.repository;

import com.beautysalon.api.v1.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByFullName(String name);
    boolean existsByFullName(String name);

    @Query("""
    SELECT i FROM Image i WHERE i.user.username = :username AND i.isPreviewImage = true
    """)
    Optional<Image> findUserPreviewImage(String username);
}
