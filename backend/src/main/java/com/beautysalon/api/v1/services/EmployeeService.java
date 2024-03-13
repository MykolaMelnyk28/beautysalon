package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.*;
import com.beautysalon.api.v1.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Service
public class EmployeeService {

    public enum Position {
        MASTER, ADMINISTRATOR
    }

    private final MasterService masterService;
    private final AdministratorService administratorService;

    public EmployeeService(MasterService masterService, AdministratorService administratorService) {
        this.masterService = masterService;
        this.administratorService = administratorService;
    }

    public void putImage(String username, Image image) {
        putImage(username, image, image.getName());
    }

    public void putImage(String username, Image image, String filename) {
        Optional<Master> masterOpt = masterService.getByUsername(username);
        Optional<Administrator> administratorOpt = administratorService.getByUsername(username);
        if (masterOpt.isEmpty() && administratorOpt.isEmpty()) {
            throw new RuntimeException("Employee not found.");
        }
        masterOpt.ifPresent(x -> {
           masterService.putImage(username, image);
        });
        administratorOpt.ifPresent(x -> {
            masterService.putImage(username, image);
        });
    }

    public List<Image> getImagesByUsername(String username) {
        Optional<Master> masterOpt = masterService.getByUsername(username);
        Optional<Administrator> administratorOpt = administratorService.getByUsername(username);
        if (masterOpt.isEmpty() && administratorOpt.isEmpty()) {
            throw new RuntimeException("Employee not found.");
        }
        if (masterOpt.isEmpty()) {
            return masterOpt.get().getUser().getImages();
        } else {
            return administratorOpt.get().getUser().getImages();
        }
    }

    public Page<Employee> getAllEmployees(EmployeeFilter filter) {
        List<Employee> allEmployees = new ArrayList<>();
        allEmployees.addAll(masterService.getAll());
        allEmployees.addAll(administratorService.getAll());
        allEmployees = allEmployees.stream()
                .filter(employee -> employee.getPosition() == filter.getPosition())
                .filter(employee -> filter.getWorkSchedules().stream().allMatch(
                                workSchedule -> employee.getWorkSchedules().contains(workSchedule)
                        )
                ).toList();
        return new PageImpl<>(allEmployees, filter.getPageable(), allEmployees.size());
    }

    public boolean isPosition(Employee employee, EmployeePosition position, boolean checkInDatabase) {
        if (employee.getPosition() != position)
            return false;
        else if (!checkInDatabase)
            return true;

        boolean isSaved = (employee.getId() != null);
        boolean existsAdministrator = false;
        boolean existsMaster = false;

        if (isSaved) {
            existsAdministrator = administratorService.getById(employee.getId()).isPresent();
            existsMaster = masterService.getById(employee.getId()).isPresent();
        } else if (employee.getUser() != null && employee.getUser().getUsername() != null) {
            isSaved = true;
            existsAdministrator = administratorService.getByUsername(employee.getUser().getUsername()).isPresent();
            existsMaster = masterService.getByUsername(employee.getUser().getUsername()).isPresent();
        }

        return !isSaved || ((position == EmployeePosition.MASTER && existsMaster) ||
                (position == EmployeePosition.ADMINISTRATOR && existsAdministrator));
    }

    public Optional<Employee> getEmployeeById(Long id) {
        Optional<Master> masterOpt = masterService.getById(id);
        Optional<Administrator> administratorOpt = administratorService.getById(id);
        if (masterOpt.isPresent() && administratorOpt.isPresent()) {
            throw new RuntimeException("More than one employee found.");
        } else if (masterOpt.isPresent()) {
            return masterOpt.map(x -> (Employee)x);
        } else if (administratorOpt.isPresent()) {
            return administratorOpt.map(x -> (Employee) x);
        } else {
            throw new RuntimeException("Employee not found.");
        }
    }

    public Optional<Employee> getEmployeeByUsername(String username) {
        Optional<Master> masterOpt = masterService.getByUsername(username);
        Optional<Administrator> administratorOpt = administratorService.getByUsername(username);
        if (masterOpt.isPresent() && administratorOpt.isPresent()) {
            throw new RuntimeException("More than one employee found.");
        } else if (masterOpt.isPresent()) {
            return masterOpt.map(x -> (Employee)x);
        } else if (administratorOpt.isPresent()) {
            return administratorOpt.map(x -> (Employee) x);
        } else {
            throw new RuntimeException("Employee not found.");
        }
    }

    public void deleteById(Long id) {
        Optional<Master> masterOpt = masterService.getById(id);
        Optional<Administrator> administratorOpt = administratorService.getById(id);

        if (masterOpt.isPresent() && administratorOpt.isPresent()) {
            throw new RuntimeException("More than one employee found.");
        } else if (masterOpt.isPresent()) {
            masterService.deleteByUsername(masterOpt.get().getEmail());
        } else if (administratorOpt.isPresent()) {
            administratorService.deleteByUsername(administratorOpt.get().getUser().getUsername());
        } else {
            throw new RuntimeException("Employee not found.");
        }
    }
}
