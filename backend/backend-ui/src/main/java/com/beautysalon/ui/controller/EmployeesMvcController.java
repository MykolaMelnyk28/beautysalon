package com.beautysalon.ui.controller;

import com.beautysalon.api.v1.dto.EmployeeDto;
import com.beautysalon.api.v1.dto.mapper.EmployeeMapper;
import com.beautysalon.domain.entities.Employee;
import com.beautysalon.domain.entities.EmployeeFilter;
import com.beautysalon.domain.entities.EmployeePosition;
import com.beautysalon.domain.services.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/panel")
public class EmployeesMvcController {

    private static class EmployeeModel {
        private Long id;
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String surName;
        private String email;
        private String phoneNumber;
        private EmployeePosition position;

        public EmployeeModel() {
        }

        public EmployeeModel(Long id, String username, String password, String firstName, String lastName, String surName, String email, String phoneNumber, EmployeePosition position) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.surName = surName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.position = position;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getSurName() {
            return surName;
        }

        public void setSurName(String surName) {
            this.surName = surName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public EmployeePosition getPosition() {
            return position;
        }

        public void setPosition(EmployeePosition position) {
            this.position = position;
        }
    }

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

    @GetMapping("/employees/create")
    public String employeesCreateGet(
            Model model,
            HttpServletRequest request
    ) {
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("employee", new EmployeeModel());
        return "pages/employee-create";
    }

    @PostMapping("/employees/create")
    public String employeesCreatePost(
            @ModelAttribute EmployeeModel empModel,
            Model model,
            HttpServletRequest request
    ) {

        return "redirect:/panel/employees";
    }

}
