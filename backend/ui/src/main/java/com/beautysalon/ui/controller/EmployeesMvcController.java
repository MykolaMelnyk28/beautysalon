package com.beautysalon.ui.controller;

import com.beautysalon.core.dto.mapper.EmployeeMapper;
import com.beautysalon.core.dto.mapper.WorkScheduleMapper;
import com.beautysalon.core.entities.*;
import com.beautysalon.core.exceptions.ResourceNotFoundException;
import com.beautysalon.core.services.EmployeeService;
import com.beautysalon.core.services.WorkScheduleService;
import com.beautysalon.core.utils.EmployeeFilter;
import com.beautysalon.ui.mapper.EmployeeModelMapper;
import com.beautysalon.ui.mapper.WorkScheduleModelMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/panel")
public class EmployeesMvcController {

    public static class EmployeeModel {
        private Long id;
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String surName;
        private String email;
        private String phoneNumber;
        private EmployeePosition position;
        private List<Long> workSchedules;

        public EmployeeModel() {
        }

        public EmployeeModel(Long id, String username, String password, String firstName, String lastName, String surName, String email, String phoneNumber, EmployeePosition position, List<Long> workSchedules) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.surName = surName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.position = position;
            this.workSchedules = workSchedules;
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

        public List<Long> getWorkSchedules() {
            return workSchedules;
        }

        public void setWorkSchedules(List<Long> workSchedules) {
            this.workSchedules = workSchedules;
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

        @Override
        public String toString() {
            return "EmployeeModel{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", surName='" + surName + '\'' +
                    ", email='" + email + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    ", position=" + position +
                    '}';
        }
    }

    private final EmployeeService employeeService;
    private final WorkScheduleService workScheduleService;
    private final WorkScheduleMapper workScheduleMapper;
    private final EmployeeMapper employeeMapper;
    private final WorkScheduleModelMapper workScheduleModelMapper;
    private final EmployeeModelMapper employeeModelMapper;

    public EmployeesMvcController(
            EmployeeService employeeService,
            EmployeeMapper employeeMapper,
            WorkScheduleService workScheduleService,
            WorkScheduleMapper workScheduleMapper,
            WorkScheduleModelMapper workScheduleModelMapper,
            EmployeeModelMapper employeeModelMapper
    ) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
        this.workScheduleService = workScheduleService;
        this.workScheduleMapper = workScheduleMapper;
        this.workScheduleModelMapper = workScheduleModelMapper;
        this.employeeModelMapper = employeeModelMapper;
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
        final List<WorkScheduleModel> workSchedules = workScheduleService.getAll().stream()
                .map(workScheduleModelMapper::toDto)
                .toList();
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("employee", new EmployeeModel());
        model.addAttribute("workSchedules", workSchedules);
        return "pages/employee-create";
    }

    @PostMapping("/employees/create")
    public String employeesCreatePost(
            @ModelAttribute EmployeeModel empModel,
            Model model,
            HttpServletRequest request
    ) {

        Employee emp = null;
        final UserEntity user = new UserEntity();
        final Set<UserRole> roles = new HashSet<>();
        user.setUsername(empModel.getUsername());
        user.setPassword(empModel.getPassword());
        if (empModel.getPosition() == EmployeePosition.MASTER) {
            emp = new Master();
            roles.add(UserRole.ROLE_MASTER);
            user.setAuthorities(roles);
        } else if (empModel.getPosition() == EmployeePosition.ADMINISTRATOR) {
            emp = new Administrator();
            roles.add(UserRole.ROLE_ADMIN);
        }
        user.setAuthorities(roles);
        emp.setUser(user);
        emp.setPosition(empModel.getPosition());
        emp.setFirstName(empModel.getFirstName());
        emp.setLastName(empModel.getLastName());
        emp.setSurName(empModel.getSurName());
        emp.setEmail(empModel.getEmail());
        emp.setPhoneNumber(empModel.getPhoneNumber());
        emp.setWorkSchedules(empModel.getWorkSchedules().stream()
                .map(id -> workScheduleService.getById(id).orElseThrow(() ->
                        new ResourceNotFoundException("WorkSchedule not found")))
                .toList());
        employeeService.create(emp);

        return "redirect:/panel/employees";
    }

}
