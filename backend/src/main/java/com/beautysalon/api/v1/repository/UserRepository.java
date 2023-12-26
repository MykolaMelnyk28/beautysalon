package com.beautysalon.api.v1.repository;

import com.beautysalon.api.v1.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
