package com.beautysalon.api.v1.dto;

import com.beautysalon.api.v1.entities.ServiceEntity;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

    public ServiceDto toDto(ServiceEntity entity) {
        if (entity == null) {
            return null;
        }

        ServiceDto dto = new ServiceDto();
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCategory(entity.getCategoryPath());
        dto.setPrice(entity.getPrice());
        dto.setCurrency(entity.getCurrency());
        dto.setDurationInMinute(entity.getDurationInMinute());

        return dto;
    }

    public ServiceEntity toEntity(ServiceDto dto) {
        if (dto == null) {
            return null;
        }

        ServiceEntity entity = new ServiceEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCategoryPath(dto.getCategory());
        entity.setPrice(dto.getPrice());
        entity.setCurrency(dto.getCurrency());
        entity.setDurationInMinute(dto.getDurationInMinute());

        return entity;
    }

}
