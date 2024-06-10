package com.beautysalon.core.dto.mapper;

import com.beautysalon.core.dto.ServiceDto;
import com.beautysalon.core.dto.mapper.base.AbstractMapper;
import com.beautysalon.core.entities.ServiceEntity;
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
