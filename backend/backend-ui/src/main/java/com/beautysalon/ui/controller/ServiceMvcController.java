package com.beautysalon.ui.controller;

import com.beautysalon.api.v1.dto.ServiceDto;
import com.beautysalon.api.v1.dto.mapper.ServiceMapper;
import com.beautysalon.api.v1.dto.validation.OnCreate;
import com.beautysalon.api.v1.dto.validation.OnPut;
import com.beautysalon.domain.entities.ServiceEntity;
import com.beautysalon.domain.services.ServiceEntityService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/panel")
public class ServiceMvcController {

    private final ServiceEntityService serviceEntityService;
    private final ServiceMapper serviceMapper;

    public ServiceMvcController(
            ServiceEntityService serviceEntityService,
            ServiceMapper serviceMapper
    ) {
        this.serviceEntityService = serviceEntityService;
        this.serviceMapper = serviceMapper;
    }

    @GetMapping("/services")
    public String serviceGet(
            Model model,
            HttpServletRequest request
    ) {
        List<ServiceDto> services = serviceEntityService.getAllServices().stream()
                .map(serviceMapper::toDto)
                .toList();



        model.addAttribute("services", services);
        model.addAttribute("currentUri", request.getRequestURI());

        return "pages/services";
    }

    @GetMapping("/services/create")
    public String servicesCreateGet(
            Model model,
            HttpServletRequest request
    ) {
        model.addAttribute("model", new ServiceDto());
        model.addAttribute("currentUri", request.getRequestURI());
        return "pages/service-create";
    }

    @PostMapping("/services/create")
    public String servicesPost(
            @Validated(OnCreate.class) @ModelAttribute("service") ServiceDto service,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "pages/service-create";
        }
        ServiceEntity entity = serviceMapper.toEntity(service);
        serviceEntityService.create(entity);
        return "redirect:/panel/services";
    }

    @GetMapping("/services/update")
    public String servicesUpdateGet(
            @RequestParam("id") Long id,
            Model model,
            HttpServletRequest request
    ) {
        ServiceDto entity = serviceEntityService.getById(id)
                .map(serviceMapper::toDto)
                .orElseThrow(() -> new RuntimeException("404 Not found."));
        model.addAttribute("model", entity);
        model.addAttribute("currentUri", request.getRequestURI());
        return "pages/service-update";
    }

    @PostMapping("/services/update")
    public String servicesUpdatePost(
            @RequestParam("id") Long id,
            @Validated(OnPut.class) @ModelAttribute("service") ServiceDto service,
            Model model,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "pages/service-update";
        }
        ServiceEntity entity = serviceMapper.toEntity(service);
        ServiceEntity updated = serviceEntityService.updateService(id, entity);
        return "redirect:/panel/services";
    }

    @GetMapping("/services/delete")
    public String servicesDelete(
            @RequestParam("id") Long id
    ) {
        serviceEntityService.deleteById(id);
        return "redirect:/panel/services";
    }
}
