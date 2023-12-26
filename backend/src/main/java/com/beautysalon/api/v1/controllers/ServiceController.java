package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1.dto.ServiceDto;
import com.beautysalon.api.v1.dto.ServiceMapper;
import com.beautysalon.api.v1.entities.ServiceEntity;
import com.beautysalon.api.v1.services.ServiceEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/services")
public class ServiceController {

    private final ServiceEntityService serviceEntityService;
    private final ServiceMapper serviceMapper;

    public ServiceController(
            ServiceEntityService serviceEntityService,
            ServiceMapper serviceMapper
    ) {
        this.serviceEntityService = serviceEntityService;
        this.serviceMapper = serviceMapper;
    }

    @PostMapping
    public ResponseEntity<ServiceDto> create(
            @RequestBody ServiceDto request
    ) {
        ServiceEntity entity = serviceMapper.toEntity(request);
        return ResponseEntity.ok(serviceMapper.toDto(
                serviceEntityService.create(entity)
        ));
    }

    @GetMapping
    public ResponseEntity<List<ServiceDto>> getServices() {
        List<ServiceEntity> services = serviceEntityService.getAllServices();
        List<ServiceDto> dtos = services.stream()
                .map(serviceMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public void deleteById(
            @PathVariable Long id
    ) {
        serviceEntityService.deleteByName(id);
    }

}
