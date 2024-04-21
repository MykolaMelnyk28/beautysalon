package com.beautysalon.api.v1.dto;

import com.beautysalon.api.v1.dto.validation.OnAlways;
import com.beautysalon.api.v1.dto.validation.OnCreate;
import com.beautysalon.api.v1.dto.validation.OnPatch;
import com.beautysalon.api.v1.dto.validation.OnPut;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

public class ClientDto {

    @Min(value = 1, message = "can not be negative.",
            groups = {OnAlways.class})
    private Long id;

    @NotBlank(message = "must not be empty",
            groups = {OnCreate.class})
    @Length(max = 20, message = "must be 20 characters or less.",
            groups = {OnAlways.class})
    private String firstName;

    @NotBlank(message = "must not be empty",
            groups = {OnCreate.class})
    @Length(max = 20, message = "must be 20 characters or less.",
            groups = {OnAlways.class})
    private String lastName;

    @NotBlank(message = "must not be empty",
            groups = {OnCreate.class, OnPut.class})
    @Email(message = "invalid email format",
            groups = {OnAlways.class})
    @Length(max = 80, message = "must be 80 characters or less.",
            groups = {OnAlways.class})
    private String email;

    @NotBlank(message = "must not be empty",
            groups = {OnCreate.class, OnPut.class})
    @Pattern(
            regexp = "(\\+\\d{1,4}[-.\\s]?)(\\(\\d{1,}\\)[-\\s]?|\\d{1,}[-.\\s]?){1,}[0-9\\s]",
            message = "invalid phone format",
            groups = {OnAlways.class}
    )
    private String phoneNumber;

    public ClientDto() {
    }

    public ClientDto(Long id, String firstName, String lastName, String email, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDto clientDto = (ClientDto) o;
        return Objects.equals(id, clientDto.id) && Objects.equals(firstName, clientDto.firstName) && Objects.equals(lastName, clientDto.lastName) && Objects.equals(email, clientDto.email) && Objects.equals(phoneNumber, clientDto.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phoneNumber);
    }
}
