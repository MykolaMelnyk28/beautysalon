package com.beautysalon.api.v1.entities;

import java.util.List;

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

    EmployeePosition getPosition();

    void setPosition(EmployeePosition position);

    UserEntity getUser();

    void setUser(UserEntity user);

    List<WorkSchedule> getWorkSchedules();

    void setWorkSchedules(List<WorkSchedule> workSchedules);

    List<Feedback> getFeedbacks();

    void setFeedbacks(List<Feedback> feedbacks);
}
