package com.beautysalon.api.v1.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "master_availability_schedule")
public class AvailabilitySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private Master master;

    private LocalTime startTime;

    private LocalTime endTime;

    @Column(nullable = false)
    private LocalDate date;

    // available, unavailable, missing
    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String note;

    @CreationTimestamp
    private LocalDateTime dateTimeCreated;

    @UpdateTimestamp
    private LocalDateTime dateTimeUpdated;

}
