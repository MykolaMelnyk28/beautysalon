package com.beautysalon.api.v1.entities;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;

public class EmployeeFilter {
    private final Pageable pageable;
    private final String position;
    private final List<WorkSchedule> workSchedules;

    public EmployeeFilter() {
        this.pageable = Pageable.unpaged(Sort.by(Sort.Direction.ASC, "id"));
        this.position = "ALL";
        this.workSchedules = List.of();
    }
    public EmployeeFilter(Pageable pageable, String position, List<WorkSchedule> workSchedules) {
        this.pageable = pageable;
        this.position = position.toUpperCase();
        this.workSchedules = workSchedules;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public EmployeePosition getPosition() {
        return EmployeePosition.valueOf(position.toUpperCase());
    }

    public List<WorkSchedule> getWorkSchedules() {
        return workSchedules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeFilter that = (EmployeeFilter) o;
        return Objects.equals(pageable, that.pageable) && Objects.equals(position, that.position) && Objects.equals(workSchedules, that.workSchedules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageable, position, workSchedules);
    }
}
