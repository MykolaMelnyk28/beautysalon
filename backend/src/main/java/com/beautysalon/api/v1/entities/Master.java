package com.beautysalon.api.v1.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "masters")
public class Master {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, optional = false, mappedBy = "master")
    private UserEntity user;

    @OneToMany(mappedBy = "master")
    private List<WorkSchedule> workSchedules;

    @OneToMany(mappedBy = "master")
    private List<AvailabilitySchedule> availabilitySchedules;

    public Master() {
    }

    public Master(Long id, String firstname, String lastname, String email, String phoneNumber, UserEntity user, List<WorkSchedule> workSchedules, List<AvailabilitySchedule> availabilitySchedules) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.user = user;
        this.workSchedules = workSchedules;
        this.availabilitySchedules = availabilitySchedules;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<WorkSchedule> getWorkSchedules() {
        return workSchedules;
    }

    public void setWorkSchedules(List<WorkSchedule> workSchedules) {
        this.workSchedules = workSchedules;
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
        return Objects.equals(id, master.id) && Objects.equals(firstname, master.firstname) && Objects.equals(lastname, master.lastname) && Objects.equals(email, master.email) && Objects.equals(phoneNumber, master.phoneNumber) && Objects.equals(user, master.user) && Objects.equals(workSchedules, master.workSchedules) && Objects.equals(availabilitySchedules, master.availabilitySchedules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, email, phoneNumber, user, workSchedules, availabilitySchedules);
    }

    //    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "master_images", joinColumns = @JoinColumn(name = "master_id"))
//    @Column(name = "image_url", nullable = false)
//    private List<String> imageUrls = new ArrayList<>();
}

