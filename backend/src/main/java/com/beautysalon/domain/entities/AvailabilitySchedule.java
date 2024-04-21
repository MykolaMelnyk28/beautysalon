package com.beautysalon.domain.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

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

    @Enumerated(EnumType.STRING)
    private ScheduleStatus state = ScheduleStatus.AVAILABLE;

    @Column(nullable = false)
    private String note;

    @CreationTimestamp
    private LocalDateTime dateTimeCreated;

    @UpdateTimestamp
    private LocalDateTime dateTimeUpdated;

    public AvailabilitySchedule() {}

    public AvailabilitySchedule(Long id, Master master, LocalTime startTime, LocalTime endTime, LocalDate date, ScheduleStatus state, String note, LocalDateTime dateTimeCreated, LocalDateTime dateTimeUpdated) {
        this.id = id;
        this.master = master;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.state = state;
        this.note = note;
        this.dateTimeCreated = dateTimeCreated;
        this.dateTimeUpdated = dateTimeUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ScheduleStatus getState() {
        return state;
    }

    public void setState(ScheduleStatus state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(LocalDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public LocalDateTime getDateTimeUpdated() {
        return dateTimeUpdated;
    }

    public void setDateTimeUpdated(LocalDateTime dateTimeUpdated) {
        this.dateTimeUpdated = dateTimeUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailabilitySchedule that = (AvailabilitySchedule) o;
        return Objects.equals(id, that.id) && Objects.equals(master, that.master) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(date, that.date) && Objects.equals(state, that.state) && Objects.equals(note, that.note) && Objects.equals(dateTimeCreated, that.dateTimeCreated) && Objects.equals(dateTimeUpdated, that.dateTimeUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, master, startTime, endTime, date, state, note, dateTimeCreated, dateTimeUpdated);
    }
}
