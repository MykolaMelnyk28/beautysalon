package com.beautysalon.api.v1.entities;


public interface Employee {

    Long getId();

    void setId(Long id);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getSurName();

    void setSurName(String surName);

    String getEmail();

    void setEmail(String email);

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    String getPosition();

    void setPosition(String position);

    UserEntity getUser();

    void setUser(UserEntity user);

}
