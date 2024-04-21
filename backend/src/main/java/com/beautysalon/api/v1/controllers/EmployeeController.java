package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1.dto.*;
import com.beautysalon.api.v1.dto.mapper.base.AutoMapper;
import com.beautysalon.domain.entities.*;
import com.beautysalon.domain.services.AdministratorService;
import com.beautysalon.domain.services.EmployeeService;
import com.beautysalon.domain.services.MasterService;
import com.beautysalon.utils.PathUtils;
import com.beautysalon.utils.WorkScheduleDtoEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AdministratorService administratorService;
    private final MasterService masterService;
    private final AutoMapper<Employee, EmployeeDto> employeeMapper;
    private final AutoMapper<WorkSchedule, WorkScheduleDto> workScheduleMapper;
    private final AutoMapper<Administrator, EmployeeDto> administratorMapper;
    private final AutoMapper<Master, EmployeeDto> masterMapper;
    private final AutoMapper<Image, ImageDto> imageMapper;

    public EmployeeController(
            EmployeeService employeeService,
            AdministratorService administratorService,
            MasterService masterService,
            AutoMapper<Employee, EmployeeDto> employeeMapper,
            AutoMapper<WorkSchedule, WorkScheduleDto> workScheduleMapper,
            AutoMapper<Administrator, EmployeeDto> administratorMapper,
            AutoMapper<Master, EmployeeDto> masterMapper,
            AutoMapper<Image, ImageDto> imageMapper
    ) {
        this.employeeService = employeeService;
        this.administratorService = administratorService;
        this.masterService = masterService;
        this.employeeMapper = employeeMapper;
        this.workScheduleMapper = workScheduleMapper;
        this.administratorMapper = administratorMapper;
        this.masterMapper = masterMapper;
        this.imageMapper = imageMapper;
    }

    @GetMapping("/administrators")
    public ResponseEntity<Page<EmployeeDto>> getAllAdministrators(
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
            Pageable pageable,
            @RequestParam(value = "wsh", required = false, defaultValue = "[]") List<WorkScheduleDto> wsh
    ) {
        List<WorkSchedule> workSchedules = wsh.stream()
                .map(workScheduleMapper::toEntity)
                .toList();
        EmployeeFilter filter = new EmployeeFilter(pageable, "all", workSchedules);
        Page<Employee> employees = employeeService.getAllEmployees(filter);
        System.out.println(employees.getContent());
        return ResponseEntity.ok(employees.map(employeeMapper::toDto));
    }

    @GetMapping("/{username}")
    public ResponseEntity<EmployeeDto> getByUsername(
            @PathVariable String username
    ) {
        return ResponseEntity.of(
                employeeService.getEmployee(username)
                        .map(employeeMapper::toDto)
        );
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteById(
            @PathVariable String username
    ) {
        employeeService.delete(username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{username}/images/{filename}")
    public ResponseEntity<ImageDto> putImage(
            @PathVariable String username,
            @PathVariable String filename,
            @RequestBody MultipartFile file
    ) {
        try {
            String[] parts = PathUtils.splitPathFilename(filename);
            Image image = new Image(
                    null,
                    parts[1],
                    parts[0],
                    filename,
                    file.getOriginalFilename(),
                    file.getSize(),
                    file.getContentType(),
                    false,
                    file.getBytes(),
                    null
            );
            Image saved = employeeService.putImage(username, image, filename);
            ImageDto response = imageMapper.toDto(saved);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error extract bytes array from MultipartFile.", e);
        }
    }

    @PutMapping("/{username}/images/preview")
    public ResponseEntity<ImageDto> putPreviewImage(
            @PathVariable String username,
            @RequestBody MultipartFile file
    ) {
        try {
            String[] parts = PathUtils.splitPathFilename(file.getName());
            Image image = new Image(
                    null,
                    "preview.jpg",
                    parts[0],
                    "preview.jpg",
                    file.getOriginalFilename(),
                    file.getSize(),
                    file.getContentType(),
                    true,
                    file.getBytes(),
                    null
            );
            Image saved = employeeService.putPreviewImage(username, image);
            ImageDto response = imageMapper.toDto(saved);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error extract bytes array from MultipartFile.", e);
        }
    }

    @GetMapping("/{username}/images")
    public ResponseEntity<List<ImageDto>> getImages(
            @PathVariable String username
    ) {
        List<Image> images = employeeService.getImages(username);
        return ResponseEntity.ok(images.stream().map(imageMapper::toDto).toList());
    }
    @GetMapping("/{username}/images/preview")
    public ResponseEntity<ImageDto> getPreviewImage(
            @PathVariable String username
    ) {
        Optional<Image> imageOpt = employeeService.getPreviewImage(username);
        return ResponseEntity.of(imageOpt.map(imageMapper::toDto));
    }


    @DeleteMapping("/{username}/images/{filename}")
    public ResponseEntity<?> deleteImage(
            @PathVariable String username,
            @PathVariable String filename
    ) {
        employeeService.deleteImage(username, filename);
        return ResponseEntity.noContent().build();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(List.class, new WorkScheduleDtoEditor());
    }
}
