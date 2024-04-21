package com.beautysalon.api.v1.dto;

import com.beautysalon.api.v1.dto.validation.OnAlways;
import com.beautysalon.api.v1.dto.validation.OnCreate;
import com.beautysalon.api.v1.dto.validation.OnPut;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AppointmentDto {

    @Min(value = 1, message = "must not be negative.",
            groups = {OnAlways.class})
    private Long id;

    @Valid
    @NotNull(message = "must not be null",
            groups = {OnCreate.class, OnPut.class})
    private ClientDto client;
    @Valid
    @NotNull(message = "must not be null",
            groups = {OnCreate.class, OnPut.class})
    private EmployeeDto master;

    @Null(message = "must not set on create",
            groups = {OnCreate.class})
    @Pattern(regexp = "^(new|confirmed|rejected_by_client|rejected_by_master|rejected_by_admin|closed)$",
            message = "invalid status value",
            groups = {OnAlways.class})
    private String status;

    @Min(value = 1, message = "must not be negative",
            groups = {OnAlways.class})
    private int totalDurationInMinute;

    @Min(value = 0, message = "must not be negative",
            groups = {OnAlways.class})
    private double totalPrice;

    @Future(message = "must not before current time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime appointmentDate;

    @Valid
    @NotNull(message = "must not be null",
            groups = {OnCreate.class, OnPut.class})
    private List<ServiceDto> services;


    public AppointmentDto() {
    }

    public AppointmentDto(Long id, ClientDto client, EmployeeDto master, String status, int totalDurationInMinute, double totalPrice, LocalDateTime appointmentDate, List<ServiceDto> services) {
        this.id = id;
        this.client = client;
        this.master = master;
        this.status = status;
        this.totalDurationInMinute = totalDurationInMinute;
        this.totalPrice = totalPrice;
        this.appointmentDate = appointmentDate;
        this.services = services;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public EmployeeDto getMaster() {
        return master;
    }

    public void setMaster(EmployeeDto master) {
        this.master = master;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalDurationInMinute() {
        return totalDurationInMinute;
    }

    public void setTotalDurationInMinute(int totalDurationInMinute) {
        this.totalDurationInMinute = totalDurationInMinute;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }
    public String getAppointmentDate(String pattern) {
        return appointmentDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public List<ServiceDto> getServices() {
        return services;
    }

    public void setServices(List<ServiceDto> services) {
        this.services = services;
    }

    public String getServicesLine() {
        return getServices().stream()
                .map(ServiceDto::getName)
                .collect(Collectors.joining());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentDto that = (AppointmentDto) o;
        return totalDurationInMinute == that.totalDurationInMinute && Double.compare(totalPrice, that.totalPrice) == 0 && Objects.equals(id, that.id) && Objects.equals(client, that.client) && Objects.equals(master, that.master) && Objects.equals(status, that.status) && Objects.equals(appointmentDate, that.appointmentDate) && Objects.equals(services, that.services);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, master, status, totalDurationInMinute, totalPrice, appointmentDate, services);
    }
}
