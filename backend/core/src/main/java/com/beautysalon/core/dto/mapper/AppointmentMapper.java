package com.beautysalon.core.dto.mapper;

import com.beautysalon.core.dto.AppointmentDto;
import com.beautysalon.core.dto.ClientDto;
import com.beautysalon.core.dto.EmployeeDto;
import com.beautysalon.core.dto.ServiceDto;
import com.beautysalon.core.dto.mapper.base.AbstractMapper;
import com.beautysalon.core.dto.mapper.base.AutoMapper;
import com.beautysalon.core.entities.Appointment;
import com.beautysalon.core.entities.Client;
import com.beautysalon.core.entities.Master;
import com.beautysalon.core.entities.ServiceEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class AppointmentMapper extends AbstractMapper<Appointment, AppointmentDto> {

    private AutoMapper<Client, ClientDto> clientMapper;
    private AutoMapper<Master, EmployeeDto> employeeMapper;
    private AutoMapper<ServiceEntity, ServiceDto> serviceMapper;

    public AppointmentMapper(AutoMapper<Client, ClientDto> clientMapper, AutoMapper<Master, EmployeeDto> employeeMapper, AutoMapper<ServiceEntity, ServiceDto> serviceMapper) {
        super();
        this.clientMapper = clientMapper;
        this.employeeMapper = employeeMapper;
        this.serviceMapper = serviceMapper;
    }

    @Override
    protected void postDtoCopy(Appointment source, AppointmentDto destination) {
        destination.setClient(clientMapper.toDto(source.getClient()));
        destination.setMaster(employeeMapper.toDto(source.getMaster()));
        destination.setTotalPrice(source.getTotalPrice().doubleValue());

        Collection<ServiceDto> servicesCol = serviceMapper.toDtoAll(source.getServices());
        if (servicesCol != null) {
            destination.setServices(new ArrayList<>(servicesCol));
        }
    }

    @Override
    protected void postEntityCopy(AppointmentDto source, Appointment destination) {
        destination.setClient(clientMapper.toEntity(source.getClient()));
        destination.setMaster(employeeMapper.toEntity(source.getMaster()));
        destination.setTotalPrice(BigDecimal.valueOf(source.getTotalPrice()));

        Collection<ServiceEntity> servicesCol = serviceMapper.toEntityAll(source.getServices());
        if (servicesCol != null) {
            destination.setServices(new ArrayList<>(servicesCol));
        }
    }
}
