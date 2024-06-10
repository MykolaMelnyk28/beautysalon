package com.beautysalon.ui.mapper;

import com.beautysalon.core.dto.mapper.base.AbstractMapper;
import com.beautysalon.core.entities.Employee;
import com.beautysalon.core.entities.EmployeePosition;
import com.beautysalon.core.entities.UserEntity;
import com.beautysalon.core.entities.UserRole;
import com.beautysalon.ui.controller.EmployeesMvcController;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class EmployeeModelMapper extends AbstractMapper<Employee, EmployeesMvcController.EmployeeModel> {

    private final WorkScheduleModelMapper workScheduleModelMapper;

    public EmployeeModelMapper(WorkScheduleModelMapper workScheduleModelMapper) {
        this.workScheduleModelMapper = workScheduleModelMapper;
    }

    @Override
    protected void postDtoCopy(Employee source, EmployeesMvcController.EmployeeModel destination) {
        if (source.getUser() != null) {
            final UserEntity user = source.getUser();
            if (user.getUsername() != null) {
                destination.setUsername(user.getUsername());
            }
            if (user.getPassword() != null) {
                destination.setPassword(user.getPassword());
            }
            if (user.getAuthorities() != null && !user.getAuthorities().isEmpty()) {
                final Set<UserRole> roles = user.getAuthorities();
                if (roles.contains(UserRole.ROLE_MASTER)) {
                    destination.setPosition(EmployeePosition.MASTER);
                } else if (roles.contains(UserRole.ROLE_ADMIN)) {
                    destination.setPosition(EmployeePosition.ADMINISTRATOR);
                }
            }
        }
//        if (source.getWorkSchedules() != null && !source.getWorkSchedules().isEmpty()) {
//            destination.setWorkSchedules(source.getWorkSchedules().stream()
//                            .map(workScheduleModelMapper::toDto)
//                    .toList());
//        }
    }

    @Override
    protected void postEntityCopy(EmployeesMvcController.EmployeeModel source, Employee destination) {
        final UserEntity user = new UserEntity();

        if (source.getUsername() != null) {
            user.setUsername(source.getUsername());
        }
        if (source.getPassword() != null) {
            user.setPassword(source.getPassword());
        }
//        if (source.getWorkSchedules() != null && !source.getWorkSchedules().isEmpty()) {
//            destination.setWorkSchedules(source.getWorkSchedules().stream()
//                            .map(workScheduleModelMapper::toEntity)
//                    .toList());
//        }
        if (source.getPosition() != null) {
            final Set<UserRole> roles = new HashSet<>();
            if (source.getPosition() == EmployeePosition.MASTER) {
                roles.add(UserRole.ROLE_MASTER);
            } else if (source.getPosition() == EmployeePosition.ADMINISTRATOR) {
                roles.add(UserRole.ROLE_ADMIN);
            }
            user.setAuthorities(roles);

        }
        destination.setUser(user);
    }


}
