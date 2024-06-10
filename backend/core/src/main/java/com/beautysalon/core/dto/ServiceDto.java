package com.beautysalon.core.dto;

import com.beautysalon.core.dto.validation.OnAlways;
import com.beautysalon.core.dto.validation.OnCreate;
import com.beautysalon.core.dto.validation.OnPut;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

public class ServiceDto {

    @Min(value = 1, message = "can not be negative.",
            groups = {OnAlways.class})
    private Long id;

    @NotBlank(message = "must not be empty",
            groups = {OnCreate.class, OnPut.class})
    @Length(max = 80, message = "text must be 80 characters or less.",
            groups = {OnAlways.class})
    private String name;

    @Length(max = 80, message = "text must be 80 characters or less.",
            groups = {OnAlways.class})
    private String category;

    @Length(max = 300, message = "text must be 300 characters or less.",
        groups = {OnAlways.class})
    private String description;

    @Min(value = 1, message = "must not be negative",
            groups = {OnAlways.class})
    private int durationInMinute;

    @Min(value = 0, message = "must not be negative",
            groups = {OnAlways.class})
    private double price;

    @NotBlank(message = "must not be empty",
            groups = {OnCreate.class, OnPut.class})
    @Length(min = 3, max = 3, message = "must be 3 characters")
    private String currency;

    public ServiceDto() {
    }

    public ServiceDto(Long id, String name, String category, String description, int durationInMinute, double price, String currency) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.durationInMinute = durationInMinute;
        this.price = price;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        if (name == null) {
            return "";
        } else if (category == null) {
            return getName();
        }
        return getCategory() + '.' + getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationInMinute() {
        return durationInMinute;
    }

    public void setDurationInMinute(int durationInMinute) {
        this.durationInMinute = durationInMinute;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceDto that = (ServiceDto) o;
        return durationInMinute == that.durationInMinute && Double.compare(price, that.price) == 0 && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(description, that.description) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, description, durationInMinute, price, currency);
    }
}
