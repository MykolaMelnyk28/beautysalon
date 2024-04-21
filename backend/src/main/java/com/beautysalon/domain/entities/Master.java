package com.beautysalon.domain.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "masters")
public class Master implements Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String firstName;

    @Column(nullable = false)
    protected String lastName;

    @Column(nullable = false)
    protected String surName;

    @Column(nullable = false, unique = true)
    protected String email;

    @Column(nullable = false, unique = true)
    protected String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected EmployeePosition position;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "work_schedule_masters",
            joinColumns = @JoinColumn(name = "master_id"),
            inverseJoinColumns = @JoinColumn(name = "work_schedule_id")
    )
    private List<WorkSchedule> workSchedules;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "master")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "master")
    private List<AvailabilitySchedule> availabilitySchedules;

    public Master() {
    }

    public Master(Long id, String firstName, String lastName, String surName, String email, String phoneNumber, UserEntity user, List<WorkSchedule> workSchedules, List<Feedback> feedbacks, List<AvailabilitySchedule> availabilitySchedules) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.surName = surName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = EmployeePosition.MASTER;
        this.user = user;
        this.workSchedules = workSchedules;
        this.feedbacks = feedbacks;
        this.availabilitySchedules = availabilitySchedules;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getSurName() {
        return surName;
    }

    @Override
    public void setSurName(String surName) {
        this.surName = surName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public EmployeePosition getPosition() {
        return position;
    }

    @Override
    public void setPosition(EmployeePosition position) {
        this.position = position;
    }

    @Override
    public UserEntity getUser() {
        return user;
    }

    @Override
    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public List<WorkSchedule> getWorkSchedules() {
        return workSchedules;
    }

    @Override
    public void setWorkSchedules(List<WorkSchedule> workSchedules) {
        this.workSchedules = workSchedules;
    }

    @Override
    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    @Override
    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<AvailabilitySchedule> getAvailabilitySchedules() {
        return availabilitySchedules;
    }

    public void setAvailabilitySchedules(List<AvailabilitySchedule> availabilitySchedules) {
        this.availabilitySchedules = availabilitySchedules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Master master = (Master) o;
        return Objects.equals(id, master.id) && Objects.equals(firstName, master.firstName) && Objects.equals(lastName, master.lastName) && Objects.equals(surName, master.surName) && Objects.equals(email, master.email) && Objects.equals(phoneNumber, master.phoneNumber) && position == master.position && Objects.equals(user, master.user) && Objects.equals(workSchedules, master.workSchedules) && Objects.equals(feedbacks, master.feedbacks) && Objects.equals(availabilitySchedules, master.availabilitySchedules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, surName, email, phoneNumber, position, user, workSchedules, feedbacks, availabilitySchedules);
    }
}

