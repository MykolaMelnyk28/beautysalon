package com.beautysalon.core.services;

import com.beautysalon.core.dto.mapper.base.AbstractMapper;
import com.beautysalon.core.repository.ServiceRepository;
import com.beautysalon.core.entities.ServiceEntity;
import com.beautysalon.core.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceEntityService {
    private final ServiceRepository serviceRepository;

    public ServiceEntityService(
            ServiceRepository serviceRepository
    ) {
        this.serviceRepository = serviceRepository;
    }

    public ServiceEntity create(ServiceEntity entity) {
        return serviceRepository.save(entity);
    }

    public Optional<ServiceEntity> getById(Long id) {
        return serviceRepository.findById(id);
    }

    public Optional<ServiceEntity> getByFullPath(String category, String name) {
        return serviceRepository.findByFullPath(category, name);
    }

    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    public ServiceEntity updateService(Long id, ServiceEntity source) {
        ServiceEntity found = getByIdOrThrow(id);
        var mapper = new AbstractMapper<ServiceEntity, ServiceEntity>() {};
        mapper.transferDtoEntity(source, found);
        ServiceEntity updated = serviceRepository.save(found);
        return updated;
    }

    public void deleteById(Long id) {
        serviceRepository.deleteById(id);
    }


    ServiceEntity getByIdOrThrow(Long id) {
        return getById(id).orElseThrow(() ->
                new ResourceNotFoundException("Service not found."));
    }

    ServiceEntity getByFullPathOrThrow(String category, String name) {
        return getByFullPath(category, name).orElseThrow(() ->
                new ResourceNotFoundException("Service not found."));
    }
}
