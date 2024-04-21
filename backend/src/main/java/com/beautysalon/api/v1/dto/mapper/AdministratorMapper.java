package com.beautysalon.api.v1.dto.mapper;

import com.beautysalon.api.v1.dto.EmployeeDto;
import com.beautysalon.api.v1.dto.ImageDto;
import com.beautysalon.api.v1.dto.mapper.base.AbstractMapper;
import com.beautysalon.api.v1.dto.mapper.base.AutoMapper;
import com.beautysalon.domain.entities.Administrator;
import com.beautysalon.domain.entities.Employee;
import com.beautysalon.domain.entities.Image;
import org.springframework.stereotype.Component;

@Component
public class AdministratorMapper extends AbstractMapper<Administrator, EmployeeDto> {
    private final AutoMapper<Image, ImageDto> imageMapper;
    private final AutoMapper<Employee, EmployeeDto> employeeMapper;

    public AdministratorMapper(
            AutoMapper<Image, ImageDto> imageMapper,
            AutoMapper<Employee, EmployeeDto> employeeMapper
    ) {
        super();
        this.imageMapper = imageMapper;
        this.employeeMapper = employeeMapper;
    }

    @Override
    protected void postDtoCopy(Administrator source, EmployeeDto destination) {
        employeeMapper.transferEntityDto(source, destination);
    }

    @Override
    protected void postEntityCopy(EmployeeDto source, Administrator destination) {
        employeeMapper.transferDtoEntity(source, destination);
    }
}
