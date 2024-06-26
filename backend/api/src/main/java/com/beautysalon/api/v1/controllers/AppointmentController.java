package com.beautysalon.api.v1.controllers;

import com.beautysalon.core.dto.AppointmentDto;
import com.beautysalon.core.dto.mapper.base.AutoMapper;
import com.beautysalon.core.entities.Appointment;
import com.beautysalon.core.services.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AutoMapper<Appointment, AppointmentDto> appointmentMapper;
    private final AppointmentService appointmentService;

    public AppointmentController(
            AutoMapper<Appointment, AppointmentDto> appointmentMapper,
            AppointmentService appointmentService
    ) {
        this.appointmentMapper = appointmentMapper;
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentDto> create(
            @RequestBody AppointmentDto request
    ) {
        Appointment appointment = appointmentMapper.toEntity(request);
        Appointment created = appointmentService.create(appointment);
        return ResponseEntity.ok(appointmentMapper.toDto(created));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAll() {
        return ResponseEntity.ok(appointmentService.getAll().stream()
                        .map(appointmentMapper::toDto)
                .toList());
    }
}
