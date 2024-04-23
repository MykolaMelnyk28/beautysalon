package com.beautysalon.ui.controller;

import com.beautysalon.api.v1.dto.AppointmentDto;
import com.beautysalon.api.v1.dto.mapper.AppointmentMapper;
import com.beautysalon.domain.services.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/panel")
public class AppointmentMvcController {

    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;

    public AppointmentMvcController(
            AppointmentService appointmentService,
        AppointmentMapper appointmentMapper
    ) {
        this.appointmentService = appointmentService;
        this.appointmentMapper = appointmentMapper;
    }

    @GetMapping("/appointments")
    public String appointmentsGet(
            Model model,
        HttpServletRequest request
    ) {
        List<AppointmentDto> appointments = appointmentService.getAll().stream()
                .map(appointmentMapper::toDto)
                .toList();
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("appointments", appointments);
        return "pages/appointments";
    }

}
