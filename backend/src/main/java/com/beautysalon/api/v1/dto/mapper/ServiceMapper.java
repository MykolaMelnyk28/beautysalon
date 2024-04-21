package com.beautysalon.api.v1.dto.mapper;

import com.beautysalon.api.v1.dto.ServiceDto;
import com.beautysalon.api.v1.dto.mapper.base.AbstractMapper;
import com.beautysalon.domain.entities.ServiceEntity;
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

}
