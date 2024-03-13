package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1.dto.*;
import com.beautysalon.api.v1.dto.mapper.base.AutoMapper;
import com.beautysalon.api.v1.entities.*;
import com.beautysalon.api.v1.services.AdministratorService;
import com.beautysalon.api.v1.services.EmployeeService;
import com.beautysalon.api.v1.services.MasterService;
import com.beautysalon.api.v1.utils.WorkScheduleDtoEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AutoMapper<Employee, EmployeeDto> employeeMapper;
    private final AutoMapper<WorkSchedule, WorkScheduleDto> workScheduleMapper;
    private final AdministratorService administratorService;
    private final AutoMapper<Administrator, EmployeeDto> administratorMapper;
    private final MasterService masterService;
    private final AutoMapper<Master, EmployeeDto> masterMapper;

    public EmployeeController(
            EmployeeService employeeService,
            AutoMapper<Employee, EmployeeDto> employeeMapper,
            AutoMapper<WorkSchedule, WorkScheduleDto> workScheduleMapper,
            AdministratorService administratorService,
            AutoMapper<Administrator, EmployeeDto> administratorMapper,
            MasterService masterService,
            AutoMapper<Master, EmployeeDto> masterMapper
    ) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
        this.workScheduleMapper = workScheduleMapper;
        this.administratorService = administratorService;
        this.administratorMapper = administratorMapper;
        this.masterService = masterService;
        this.masterMapper = masterMapper;
    }

    @GetMapping("/administrators")
    public ResponseEntity<Page<EmployeeDto>> getAllAdministrators(
//            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
//            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
//            @RequestParam(value = "sort", required = false, defaultValue = "id,asc") Sort sort,
            Pageable pageable,
            @RequestParam(value = "wsh", required = false, defaultValue = "[]") List<WorkScheduleDto> wsh
    ) {
        List<WorkSchedule> workSchedules = wsh.stream().map(workScheduleMapper::toEntity).toList();
        EmployeeFilter filter = new EmployeeFilter(pageable, "administrator", workSchedules);
        Page<Employee> employees = employeeService.getAllEmployees(filter);
        return ResponseEntity.ok(employees.map(employeeMapper::toDto));
    }

    @GetMapping("/masters")
    public ResponseEntity<Page<EmployeeDto>> getAllMasters(
            //@RequestParam(value = "page", required = false, defaultValue = "0") int page,
            //@RequestParam(value = "size", required = false, defaultValue = "10") int size,
            //@RequestParam(value = "sort", required = false, defaultValue = "id,asc") Sort sort,
            Pageable pageable,
            @RequestParam(value = "wsh", required = false, defaultValue = "[]") List<WorkScheduleDto> wsh
    ) {
        List<WorkSchedule> workSchedules = wsh.stream().map(workScheduleMapper::toEntity).toList();
        EmployeeFilter filter = new EmployeeFilter(pageable, "master", workSchedules);
        Page<Employee> employees = employeeService.getAllEmployees(filter);
        return ResponseEntity.ok(employees.map(employeeMapper::toDto));
    }

    @PostMapping("/masters/{id}/availability")
    public ResponseEntity<ResponseAvailabilityDateTime> isAvailableToDate(
            @PathVariable Long id,
            @RequestBody RequestAvailabilityDateTime request
    ) {
        if (request.getStartDatetime().isAfter(request.getEndDatetime())) {
            return ResponseEntity.badRequest().build();
        }

        boolean accessibility = masterService.isAvailableRangeDateTime(id, request.getStartDatetime(), request.getEndDatetime());

        ResponseAvailabilityDateTime response = new ResponseAvailabilityDateTime();
        response.setStartDatetime(request.getStartDatetime());
        response.setEndDatetime(request.getEndDatetime());
        response.setAccessibility(accessibility);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeDto>> getAllEmployees(
            //@RequestParam(value = "page", required = false, defaultValue = "0") int page,
            //@RequestParam(value = "size", required = false, defaultValue = "10") int size,
            //@RequestParam(value = "sort", required = false, defaultValue = "id,asc") Sort sort,
            Pageable pageable,
            @RequestParam(value = "wsh", required = false, defaultValue = "[]") List<WorkScheduleDto> wsh
    ) {
        //Pageable pageable = PageRequest.of(page, size, sort);
        List<WorkSchedule> workSchedules = wsh.stream()
                .map(workScheduleMapper::toEntity)
                .toList();
        EmployeeFilter filter = new EmployeeFilter(pageable, "all", workSchedules);
        Page<Employee> employees = employeeService.getAllEmployees(filter);
        return ResponseEntity.ok(employees.map(employeeMapper::toDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}/images")
    public ResponseEntity<?> getImages(
            @PathVariable String username
    ) {

        return ResponseEntity.ok(null);
    }

    @PutMapping("/{username}/images/{filename}")
    public ResponseEntity<?> create(
            @PathVariable String username,
            @PathVariable String filename
    ) {
        return ResponseEntity.ok(null);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(List.class, new WorkScheduleDtoEditor());
    }
}
