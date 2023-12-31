package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.ServiceEntity;
import com.beautysalon.api.v1.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceEntityService {
    private final ServiceRepository serviceRepository;

    public ServiceEntityService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public ServiceEntity create(ServiceEntity entity) {
        return serviceRepository.save(entity);
    }

    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    public void deleteById(Long id) {
        serviceRepository.deleteById(id);
    }
}
