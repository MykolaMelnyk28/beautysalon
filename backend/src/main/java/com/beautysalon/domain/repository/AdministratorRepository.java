package com.beautysalon.domain.repository;

import com.beautysalon.domain.entities.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    Optional<Administrator> findByEmail(String email);
    @Query("""
    SELECT a FROM Administrator a WHERE a.user.username = :username
    """)
    Optional<Administrator> findByUsername(String username);
}
