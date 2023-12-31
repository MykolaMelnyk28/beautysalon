package com.beautysalon.api.v1.dto;

import com.beautysalon.api.v1.dto.mapper.base.AbstractMapper;
import com.beautysalon.api.v1.entities.ServiceEntity;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper extends AbstractMapper<ServiceEntity, ServiceDto> {

    @Override
    protected void postDtoCopy(ServiceEntity source, ServiceDto destination) {
        destination.setCategory(source.getCategoryPath());
    }

    @Override
    protected void postEntityCopy(ServiceDto source, ServiceEntity destination) {
        destination.setCategoryPath(source.getCategory());
    }

    //    public ServiceDto toDto(ServiceEntity entity) {
//        ServiceDto dto = super.toDto(entity);
//        if (dto != null) {
//            dto.setCategory(entity.getCategoryPath());
//        }
//
////        dto.setName(entity.getName());
////        dto.setDescription(entity.getDescription());
////        dto.setPrice(entity.getPrice());
////        dto.setCurrency(entity.getCurrency());
////        dto.setDurationInMinute(entity.getDurationInMinute());
//
//        return dto;
//    }
//
//
//
//    public ServiceEntity toEntity(ServiceDto dto) {
//        ServiceEntity entity = super.toEntity(dto);
//        if (entity != null) {
//            entity.setCategoryPath(dto.getCategory());
//        }
//
////        entity.setName(dto.getName());
////        entity.setDescription(dto.getDescription());
////        entity.setPrice(dto.getPrice());
////        entity.setCurrency(dto.getCurrency());
////        entity.setDurationInMinute(dto.getDurationInMinute());
//
//        return entity;
//    }

}
