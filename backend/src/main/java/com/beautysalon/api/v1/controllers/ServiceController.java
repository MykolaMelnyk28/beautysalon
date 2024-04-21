package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1.dto.ServiceDto;
import com.beautysalon.api.v1.dto.mapper.base.AutoMapper;
import com.beautysalon.domain.entities.ServiceEntity;
import com.beautysalon.domain.services.ServiceEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {

    private final ServiceEntityService serviceEntityService;
    private final AutoMapper<ServiceEntity, ServiceDto> serviceMapper;

    public ServiceController(
            ServiceEntityService serviceEntityService,
            AutoMapper<ServiceEntity, ServiceDto> serviceMapper
    ) {
        this.serviceEntityService = serviceEntityService;
        this.serviceMapper = serviceMapper;
    }

    @PostMapping
    public ResponseEntity<ServiceDto> create(
            @RequestBody ServiceDto request
    ) {
        ServiceEntity entity = serviceMapper.toEntity(request);
        ServiceEntity saved = serviceEntityService.create(entity);
        ServiceDto response = serviceMapper.toDto(saved);
        return ResponseEntity
                .created(URI.create("/v1/services/"+saved.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ServiceDto>> getServices() {
        List<ServiceEntity> services = serviceEntityService.getAllServices();
        List<ServiceDto> dtos = services.stream()
                .map(serviceMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDto> getServiceById(
            @PathVariable Long id
    ) {
        return ResponseEntity.of(serviceEntityService.getById(id)
                .map(serviceMapper::toDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceDto> updateService(
            @PathVariable Long id,
            @RequestBody ServiceDto serviceDto
    ) {
        ServiceEntity entity = serviceMapper.toEntity(serviceDto);
        ServiceEntity updated = serviceEntityService.updateService(id, entity);
        return ResponseEntity.ok(serviceMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {
        serviceEntityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
