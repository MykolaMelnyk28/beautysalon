package com.beautysalon.ui.controller;

import com.beautysalon.api.v1.dto.EmployeeDto;
import com.beautysalon.api.v1.dto.mapper.EmployeeMapper;
import com.beautysalon.domain.entities.Employee;
import com.beautysalon.domain.entities.EmployeeFilter;
import com.beautysalon.domain.services.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/panel")
public class EmployeesMvcController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeesMvcController(
            EmployeeService employeeService,
            EmployeeMapper employeeMapper
    ) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping("/employees")
    public String employeesGet(
            Model model,
            HttpServletRequest request
    ) {
        List<Employee> employees = employeeService.getAllEmployees(new EmployeeFilter())
                .getContent();
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("employees", employees);
        return "pages/employees";
    }

}
