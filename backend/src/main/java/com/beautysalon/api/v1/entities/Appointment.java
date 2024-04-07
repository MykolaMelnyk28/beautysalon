package com.beautysalon.api.v1.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "master_id")
    private Master master;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Column(nullable = false)
    private int totalDurationInMinutes;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private LocalDateTime appointmentDate;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    private LocalDateTime dateUpdated;

    @ManyToMany
    @JoinTable(
            name = "appointments_services",
            joinColumns = {@JoinColumn(name = "service_id")},
            inverseJoinColumns = {@JoinColumn(name = "appointment_id")}
    )
    private List<ServiceEntity> services;

    public Appointment() {}

    public Appointment(Long id, Client client, Master master, int totalDurationInMinutes, BigDecimal totalPrice, LocalDateTime appointmentDate, LocalDateTime dateCreated, LocalDateTime dateUpdated, List<ServiceEntity> services) {
        this.id = id;
        this.client = client;
        this.master = master;
        this.totalDurationInMinutes = totalDurationInMinutes;
        this.totalPrice = totalPrice;
        this.appointmentDate = appointmentDate;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.services = services;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public int getTotalDurationInMinutes() {
        return totalDurationInMinutes;
    }

    public void setTotalDurationInMinutes(int totalDurationInMinutes) {
        this.totalDurationInMinutes = totalDurationInMinutes;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public List<ServiceEntity> getServices() {
        return services;
    }

    public void setServices(List<ServiceEntity> services) {
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return totalDurationInMinutes == that.totalDurationInMinutes && Objects.equals(id, that.id) && Objects.equals(client, that.client) && Objects.equals(master, that.master) && status == that.status && Objects.equals(totalPrice, that.totalPrice) && Objects.equals(appointmentDate, that.appointmentDate) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(dateUpdated, that.dateUpdated) && Objects.equals(services, that.services);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, master, status, totalDurationInMinutes, totalPrice, appointmentDate, dateCreated, dateUpdated, services);
    }
}

