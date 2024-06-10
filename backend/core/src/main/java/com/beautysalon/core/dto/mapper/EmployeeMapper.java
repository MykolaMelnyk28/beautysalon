package com.beautysalon.core.dto.mapper;

import com.beautysalon.core.dto.EmployeeDto;
import com.beautysalon.core.dto.ImageDto;
import com.beautysalon.core.dto.mapper.base.AbstractMapper;
import com.beautysalon.core.dto.mapper.base.AutoMapper;
import com.beautysalon.core.entities.Employee;
import com.beautysalon.core.entities.EmployeePosition;
import com.beautysalon.core.entities.Image;
import com.beautysalon.core.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EmployeeMapper extends AbstractMapper<Employee, EmployeeDto> {
    private final AutoMapper<Image, ImageDto> imageMapper;

    public EmployeeMapper(
            AutoMapper<Image, ImageDto> imageMapper
    ) {
        super();
        this.imageMapper = imageMapper;
    }

    @Override
    protected void postDtoCopy(Employee source, EmployeeDto destination) {
        destination.setPosition(source.getPosition().name());
        if (source.getUser() != null) {
            if (source.getUser().getImages() != null) {
                destination.setImageUrl(source.getUser().getImages().stream()
                        .map(imageMapper::toDto)
                        .toList());
            } else {
              destination.setImageUrl(new ArrayList<>());
            }
            destination.setUsername(source.getUser().getUsername());
        }
    }

    @Override
    protected void postEntityCopy(EmployeeDto source, Employee destination) {
        destination.setPosition(EmployeePosition.valueOf(source.getPosition().toUpperCase()));
        if (source.getImageUrl() != null) {
            if (destination.getUser() == null) {
                destination.setUser(new UserEntity());
            }
            destination.getUser().setImages(source.getImageUrl().stream()
                        .map(imageMapper::toEntity)
                        .toList());
        } else {
            destination.getUser().setImages(new ArrayList<>());
        }
        destination.getUser().setUsername(source.getUsername());
    }
}
