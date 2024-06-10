package com.beautysalon.core.dto.mapper;

import com.beautysalon.core.dto.EmployeeDto;
import com.beautysalon.core.dto.ImageDto;
import com.beautysalon.core.dto.mapper.base.AbstractMapper;
import com.beautysalon.core.dto.mapper.base.AutoMapper;
import com.beautysalon.core.entities.Administrator;
import com.beautysalon.core.entities.Employee;
import com.beautysalon.core.entities.Image;
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
