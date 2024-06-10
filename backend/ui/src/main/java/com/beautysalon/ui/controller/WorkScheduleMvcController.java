package com.beautysalon.ui.controller;

import com.beautysalon.core.dto.mapper.ServiceMapper;
import com.beautysalon.core.dto.mapper.WorkScheduleMapper;
import com.beautysalon.core.entities.WorkSchedule;
import com.beautysalon.core.exceptions.ResourceNotFoundException;
import com.beautysalon.core.services.WorkScheduleService;
import com.beautysalon.ui.mapper.WorkScheduleModelMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/panel")
public class WorkScheduleMvcController {

    private WorkScheduleService workScheduleService;
    private final WorkScheduleMapper workScheduleMapper;
    private final ServiceMapper serviceMapper;
    private final WorkScheduleModelMapper workScheduleModelMapper;

    public WorkScheduleMvcController(
            WorkScheduleService workScheduleService,
            WorkScheduleMapper workScheduleMapper,
            WorkScheduleModelMapper workScheduleModelMapper,
            ServiceMapper serviceMapper
    ) {
        this.workScheduleService = workScheduleService;
        this.workScheduleMapper = workScheduleMapper;
        this.workScheduleModelMapper = workScheduleModelMapper;
        this.serviceMapper = serviceMapper;
    }

    @GetMapping("/schedules")
    public String Get(
            Model model,
            HttpServletRequest request
    ) {
        List<WorkScheduleModel> schedules = workScheduleService.getAll().stream()
                .map(workScheduleModelMapper::toDto)
                .toList();

        model.addAttribute("schedules", schedules);
        model.addAttribute("currentUri", request.getRequestURI());

        return "pages/schedules";
    }

    @GetMapping("/schedules/create")
    public String servicesCreateGet(
            Model model,
            HttpServletRequest request
    ) {
        model.addAttribute("model", new WorkScheduleModel());
        model.addAttribute("currentUri", request.getRequestURI());
        return "pages/schedule-create";
    }

    @PostMapping("/schedules/create")
    public String servicesPost(
            WorkScheduleModel schedule,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "pages/schedule-create";
        }
        //ServiceEntity entity = serviceMapper.toEntity(service);
        WorkSchedule entity = workScheduleModelMapper.toEntity(schedule);
        workScheduleService.create(entity);
        return "redirect:/panel/schedules";
    }

    @GetMapping("/schedules/update")
    public String servicesUpdateGet(
            @RequestParam("id") Long id,
            Model model,
            HttpServletRequest request
    ) {
        WorkScheduleModel schedule = workScheduleService.getById(id)
                .map(workScheduleModelMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("404 Not found."));
        model.addAttribute("model", schedule);
        model.addAttribute("currentUri", request.getRequestURI());
        return "pages/schedule-update";
    }

    @PostMapping("/schedules/update")
    public String servicesUpdatePost(
            @RequestParam("id") Long id,
            WorkScheduleModel schedule,
            Model model,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "pages/service-update";
        }
        //ServiceEntity entity = serviceMapper.toEntity(service);
        WorkSchedule entity = workScheduleModelMapper.toEntity(schedule);
        WorkSchedule updated = workScheduleService.updateById(id, entity);
        return "redirect:/panel/schedules";
    }

    @GetMapping("/schedules/delete")
    public String servicesDelete(
            @RequestParam("id") Long id
    ) {
        workScheduleService.deleteById(id);
        return "redirect:/panel/schedules";
    }

}
