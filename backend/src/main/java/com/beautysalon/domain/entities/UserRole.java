package com.beautysalon.domain.entities;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;


public enum UserRole implements GrantedAuthority {
    ROLE_USER,
    ROLE_MASTER,
    ROLE_ADMIN,
    ROLE_SYS_ADMIN,
    ROLE_ROOT_ADMIN,
    ROLE_SERVICE_EDITOR,
    ROLE_IMAGE_EDITOR,
    ROLE_EMPLOYEE_EDITOR,
    ROLE_APPOINTMENT_EDITOR,
    ROLE_ACCOUNT_EDITOR,
    ROLE_SCHEDULES_EDITOR;

    @Override
    public String getAuthority() {
        return name();
    }

    public String getSimpleName() {
        return name().substring(5);
    }


}
