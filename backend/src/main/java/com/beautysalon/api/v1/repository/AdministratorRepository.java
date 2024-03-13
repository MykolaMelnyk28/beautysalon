package com.beautysalon.api.v1.repository;

import com.beautysalon.api.v1.entities.Administrator;
import com.beautysalon.api.v1.entities.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
