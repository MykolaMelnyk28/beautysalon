package com.beautysalon.domain.services;

import com.beautysalon.api.v1.exceptions.ResourceAlreadyExists;
import com.beautysalon.api.v1.exceptions.ResourceNotFoundException;
import com.beautysalon.domain.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class EmployeeService {

    public static final Function<Master, Employee> MASTER_IDENTITY = m -> m;
    public static final Function<Administrator, Employee> ADMINISTRATOR_IDENTITY = a -> a;

    private final MasterService masterService;
    private final AdministratorService administratorService;

    public EmployeeService(MasterService masterService, AdministratorService administratorService) {
        this.masterService = masterService;
        this.administratorService = administratorService;
    }

    public Image putImage(String username, Image image) {
        return putImage(username, image, image.getName());
    }

    public Image putImage(String username, Image image, String filename) {
        return fromEmployee(username,
                m -> masterService.putImage(username, image, filename),
                a -> administratorService.putImage(username, image, filename)
        ).orElseThrow(() -> new RuntimeException("Error save image."));
    }

    public Image putPreviewImage(String username, Image image) {
        return fromEmployee(username,
                m -> masterService.putPreviewImage(username, image),
                a -> administratorService.putPreviewImage(username, image)
        ).orElseThrow(() -> new RuntimeException("Error save image."));
    }

    public List<Image> getImages(String username) {
        return fromEmployee(username,
                m -> m.getUser().getImages(),
                a -> a.getUser().getImages()
        ).orElse(new ArrayList<>());
    }

    public Optional<Image> getPreviewImage(String username) {
        return fromEmployee(username,
                m -> masterService.getPreviewImage(username),
                a -> administratorService.getPreviewImage(username)
                ).get();
    }

    public void deleteImage(String username, String filename) {
        fromEmployee(username, m -> {
            masterService.deleteImage(username, filename);
            return null;
        }, a -> {
            administratorService.deleteImage(username, filename);
            return null;
        });
    }

    public Page<Employee> getAllEmployees(EmployeeFilter filter) {
        List<Employee> allEmployees = new ArrayList<>();
        if (filter.getPosition() == EmployeePosition.ALL ||
            filter.getPosition() == EmployeePosition.MASTER) {
            allEmployees.addAll(masterService.getAll());
        }
        if (filter.getPosition() == EmployeePosition.ALL ||
                filter.getPosition() == EmployeePosition.ADMINISTRATOR) {
            allEmployees.addAll(administratorService.getAll());
        }
        allEmployees = allEmployees.stream()
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

        Optional<Employee> found = fromEmployee(employee.getUser().getUsername(), MASTER_IDENTITY, ADMINISTRATOR_IDENTITY);

        boolean isSaved = found.isPresent();
        boolean existsAdministrator = found.stream().allMatch(x -> x.getPosition() == EmployeePosition.ADMINISTRATOR);
        boolean existsMaster = found.stream().allMatch(x -> x.getPosition() == EmployeePosition.MASTER);

        return !isSaved || ((position == EmployeePosition.MASTER && existsMaster) ||
                (position == EmployeePosition.ADMINISTRATOR && existsAdministrator));
    }


    public Optional<Employee> getEmployee(String username) {
        return fromEmployee(username, x -> x, x -> x);
    }

    public void delete(String username) {
        Optional<EmployeePosition> position = fromEmployee(username,
                x -> x.getPosition(),
                x -> x.getPosition()
        );

        fromEmployee(username, m -> {
            masterService.deleteByUsername(username);
            return null;
        }, a -> {
            administratorService.deleteByUsername(username);
            return null;
        });
    }

    <T extends Employee> Optional<T> getEmployeeAs(String username, Class<T> type) {
        try {
            return fromEmployee(username, MASTER_IDENTITY, ADMINISTRATOR_IDENTITY).map(type::cast);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    <T> Optional<T> fromEmployee(
            String username,
            Function<Master, T> masterSupplier,
            Function<Administrator, T> administratorSupplier
    ) {
        Optional<Master> masterOpt = masterService.getByUsername(username);
        Optional<Administrator> administratorOpt = administratorService.getByUsername(username);
        return fromEmployee(masterSupplier, administratorSupplier, masterOpt, administratorOpt);
    }

    <T> Optional<T> fromEmployee(
            Function<Master, T> masterSupplier,
            Function<Administrator, T> administratorSupplier,
            Optional<Master> masterOpt,
            Optional<Administrator> administratorOpt
    ) {
        if (masterOpt.isPresent() && administratorOpt.isPresent()) {
            throw new ResourceAlreadyExists("More than one employee found.");
        } else if (masterOpt.isPresent()) {
            return Optional.of( masterSupplier.apply(masterOpt.get()) );
        } else if (administratorOpt.isPresent()) {
            return Optional.of( administratorSupplier.apply(administratorOpt.get()) );
        } else {
            throw new ResourceNotFoundException("Employee not found.");
        }
    }
}
