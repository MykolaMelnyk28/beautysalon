package com.beautysalon.core.repository;

import com.beautysalon.core.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByFullName(String name);
    boolean existsByFullName(String name);

    @Query("""
    SELECT i FROM Image i WHERE i.user.username = :username AND i.isPreviewImage = true
    """)
    Optional<Image> findUserPreviewImage(String username);
}
