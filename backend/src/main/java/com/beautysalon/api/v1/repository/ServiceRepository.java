package com.beautysalon.api.v1.repository;

import com.beautysalon.api.v1.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

}
