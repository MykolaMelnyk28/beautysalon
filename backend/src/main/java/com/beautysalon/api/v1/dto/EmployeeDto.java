package com.beautysalon.api.v1.dto;

import com.beautysalon.api.v1.dto.validation.OnAlways;
import com.beautysalon.api.v1.dto.validation.OnCreate;
import com.beautysalon.api.v1.dto.validation.OnPut;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Objects;

public class EmployeeDto {

    @Min(value = 1, message = "can not be negative.",
            groups = {OnAlways.class})
    private Long id;

    @Nullable
    @Length(max = 20, message = "must be 20 characters or less.",
            groups = {OnAlways.class})
    private String firstName;

    @Nullable
    @Length(max = 20, message = "must be 20 characters or less.",
            groups = {OnAlways.class})
    private String lastName;

    @Nullable
    @Length(max = 20, message = "must be 20 characters or less.",
            groups = {OnAlways.class})
    private String surName;

    @NotBlank(message = "must not be empty",
            groups = {OnCreate.class, OnPut.class})
    @Length(max = 80, message = "must be 80 characters or less.",
            groups = {OnAlways.class})
    @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9_]*$", message = "invalid format",
            groups = {OnAlways.class})
    private String username;

    @NotBlank(message = "must not be empty",
            groups = {OnCreate.class, OnPut.class})
    @Email(message = "invalid format",
            groups = {OnCreate.class, OnPut.class})
    @Length(max = 80, message = "must be 80 characters or less.",
            groups = {OnCreate.class, OnPut.class})
    private String email;

    @NotBlank(message = "must not be empty",
            groups = {OnCreate.class, OnPut.class})
    @Pattern(
            regexp = "(\\+\\d{1,4}[-.\\s]?)(\\(\\d{1,}\\)[-\\s]?|\\d{1,}[-.\\s]?){1,}[0-9\\s]",
            message = "invalid phone format",
            groups = {OnAlways.class}
    )
    private String phoneNumber;

    @NotNull(message = "must be not null",
            groups = {OnCreate.class, OnPut.class})
    @Pattern(regexp = "^(MASTER|ADMINISTRATOR)$",
            message = "", groups = {OnAlways.class})
    private String position;

    @Valid
    private List<ImageDto> imageUrl;

    @Valid
    private List<WorkScheduleDto> workSchedule;

    public EmployeeDto() {}

    public EmployeeDto(Long id, @Nullable String firstName, @Nullable String lastName, @Nullable String surName, String username, String email, String phoneNumber, String position, List<ImageDto> imageUrl, List<WorkScheduleDto> workSchedule) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.surName = surName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.imageUrl = imageUrl;
        this.workSchedule = workSchedule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<ImageDto> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<ImageDto> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<WorkScheduleDto> getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(List<WorkScheduleDto> workSchedule) {
        this.workSchedule = workSchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto that = (EmployeeDto) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(surName, that.surName) && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(position, that.position) && Objects.equals(imageUrl, that.imageUrl) && Objects.equals(workSchedule, that.workSchedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, surName, username, email, phoneNumber, position, imageUrl, workSchedule);
    }
}
