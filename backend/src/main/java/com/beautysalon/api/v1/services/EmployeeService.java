package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final MasterService masterService;
    private final AdministratorService administratorService;

    public EmployeeService(MasterService masterService, AdministratorService administratorService) {
        this.masterService = masterService;
        this.administratorService = administratorService;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.addAll(masterService.getAll());
        employees.addAll(administratorService.getAll());
        return employees;
    }
}
