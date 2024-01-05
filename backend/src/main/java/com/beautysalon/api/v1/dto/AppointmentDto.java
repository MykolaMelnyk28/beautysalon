package com.beautysalon.api.v1.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class AppointmentDto {
    private Long id;
    private ClientDto client;
    private EmployeeDto master;
    private int totalDurationInMinute;
    private double totalPrice;
    private LocalDateTime appointmentDate;
    private List<ServiceDto> services;


    public AppointmentDto() {
    }

    public AppointmentDto(Long id, ClientDto client, EmployeeDto master, int totalDurationInMinute, double totalPrice, LocalDateTime appointmentDate, List<ServiceDto> services) {
        this.id = id;
        this.client = client;
        this.master = master;
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

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public List<ServiceDto> getServices() {
        return services;
    }

    public void setServices(List<ServiceDto> services) {
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentDto that = (AppointmentDto) o;
        return totalDurationInMinute == that.totalDurationInMinute && Double.compare(totalPrice, that.totalPrice) == 0 && Objects.equals(id, that.id) && Objects.equals(client, that.client) && Objects.equals(master, that.master) && Objects.equals(appointmentDate, that.appointmentDate) && Objects.equals(services, that.services);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, master, totalDurationInMinute, totalPrice, appointmentDate, services);
    }
}
