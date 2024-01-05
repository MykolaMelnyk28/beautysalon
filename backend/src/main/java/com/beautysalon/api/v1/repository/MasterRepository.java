package com.beautysalon.api.v1.repository;

import com.beautysalon.api.v1.entities.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
    Optional<Master> findByEmail(String email);
    List<Master> findAllByPosition(String position);
}
