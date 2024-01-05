package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1.dto.EmployeeDto;
import com.beautysalon.api.v1.dto.RequestAvailabilityDateTime;
import com.beautysalon.api.v1.dto.ResponseAvailabilityDateTime;
import com.beautysalon.api.v1.dto.mapper.base.AutoMapper;
import com.beautysalon.api.v1.entities.Administrator;
import com.beautysalon.api.v1.entities.Employee;
import com.beautysalon.api.v1.entities.Master;
import com.beautysalon.api.v1.services.AdministratorService;
import com.beautysalon.api.v1.services.EmployeeService;
import com.beautysalon.api.v1.services.MasterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {

    @RestController
    @RequestMapping("/v1/employees/administrators")
    public static class AdministratorController {
        private final AdministratorService administratorService;
        private final AutoMapper<Administrator, EmployeeDto> administratorMapper;

        public AdministratorController(AdministratorService administratorService, AutoMapper<Administrator, EmployeeDto> administratorMapper) {
            this.administratorService = administratorService;
            this.administratorMapper = administratorMapper;
        }

        @GetMapping
        public ResponseEntity<List<EmployeeDto>> getAllAdministrators() {
            List<Administrator> administrators = administratorService.getAll();
            return ResponseEntity.ok(administrators.stream()
                            .map(administratorMapper::toDto)
                    .toList());
        }
    }

    @RestController
    @RequestMapping("/v1/employees/masters")
    public static class MasterController {

        private final MasterService masterService;
        private final AutoMapper<Master, EmployeeDto> masterMapper;

        public MasterController(
                MasterService masterService,
                AutoMapper<Master, EmployeeDto> masterMapper
        ) {
            this.masterService = masterService;
            this.masterMapper = masterMapper;
        }

        @GetMapping
        public ResponseEntity<List<EmployeeDto>> getAll() {
            List<Master> masters = masterService.getAll();
            return ResponseEntity.ok(masters.stream()
                    .map(masterMapper::toDto)
                    .toList());
        }

        @PostMapping("/{id}/availability")
        public ResponseEntity<ResponseAvailabilityDateTime> isAvailableToDate(
                @PathVariable Long id,
                @RequestBody RequestAvailabilityDateTime request
        ) {
            if (request.getStartDatetime().isAfter(request.getEndDatetime())) {
                return ResponseEntity.unprocessableEntity().build();
            }

            boolean accessibility = masterService.isAvailableRangeDateTime(id, request.getStartDatetime(), request.getEndDatetime());

            ResponseAvailabilityDateTime response = new ResponseAvailabilityDateTime();
            response.setStartDatetime(request.getStartDatetime());
            response.setEndDatetime(request.getEndDatetime());
            response.setAccessibility(accessibility);

            return ResponseEntity.ok(response);
        }
    }

    private final EmployeeService employeeService;
    private final AutoMapper<Employee, EmployeeDto> employeeMapper;

    public EmployeeController(
            EmployeeService employeeService,
            AutoMapper<Employee, EmployeeDto> employeeMapper
    ) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees.stream()
                        .map(employeeMapper::toDto)
                .toList());
    }
}
