package com.beautysalon.domain.repository;

import com.beautysalon.domain.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    @Query("""
        SELECT se FROM ServiceEntity se
        WHERE se.categoryPath = :category AND se.name = :name
        """)
    Optional<ServiceEntity> findByFullPath(String category, String name);
}
