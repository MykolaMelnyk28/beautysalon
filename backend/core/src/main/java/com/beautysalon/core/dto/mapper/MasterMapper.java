package com.beautysalon.core.dto.mapper;

import com.beautysalon.core.dto.EmployeeDto;
import com.beautysalon.core.dto.WorkScheduleDto;
import com.beautysalon.core.dto.mapper.base.AbstractMapper;
import com.beautysalon.core.dto.mapper.base.AutoMapper;
import com.beautysalon.core.entities.Employee;
import com.beautysalon.core.entities.Master;
import com.beautysalon.core.entities.WorkSchedule;
import org.springframework.stereotype.Component;

@Component
public class MasterMapper extends AbstractMapper<Master, EmployeeDto> {

    private final AutoMapper<WorkSchedule, WorkScheduleDto> workScheduleMapper;
    private final AutoMapper<Employee, EmployeeDto> employeeMapper;

    public MasterMapper(
            AutoMapper<WorkSchedule, WorkScheduleDto> workScheduleMapper,
            AutoMapper<Employee, EmployeeDto> employeeMapper
    ) {
        super();
        this.workScheduleMapper = workScheduleMapper;
        this.employeeMapper = employeeMapper;
    }

    @Override
    protected void postDtoCopy(Master source, EmployeeDto destination) {
        employeeMapper.transferEntityDto(source, destination);
        if (source.getWorkSchedules() != null) {
            destination.setWorkSchedule(source.getWorkSchedules().stream()
                    .map(workScheduleMapper::toDto)
                    .toList());
        }
        System.out.println(destination.getId());
    }

    @Override
    protected void postEntityCopy(EmployeeDto source, Master destination) {
        employeeMapper.transferDtoEntity(source, destination);
        if (source.getWorkSchedule() != null) {
            destination.setWorkSchedules(source.getWorkSchedule().stream()
                    .map(workScheduleMapper::toEntity)
                    .toList());
        }
    }
}
