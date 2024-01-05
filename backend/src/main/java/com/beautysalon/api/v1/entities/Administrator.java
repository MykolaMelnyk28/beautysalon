package com.beautysalon.api.v1.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "administrators")
public class Administrator implements Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    protected String position;

    @OneToOne(cascade = CascadeType.ALL, optional = false, mappedBy = "administrator")
    protected UserEntity user;

    public Administrator() {

    }

    public Administrator(Long id, String firstName, String lastName, String surName, String email, String phoneNumber, String position, UserEntity user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.surName = surName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

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
    public String getPosition() {
        return position;
    }

    @Override
    public void setPosition(String position) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrator that = (Administrator) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phoneNumber, user);
    }
}
