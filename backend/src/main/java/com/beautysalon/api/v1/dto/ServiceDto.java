package com.beautysalon.api.v1.dto;

import java.util.Objects;

public class ServiceDto {
    private String name;
    private String category;
    private String description;
    private int durationInMinute;
    private double price;
    private String currency;

    public ServiceDto() {
    }

    public ServiceDto(String name, String category, String description, int durationInMinute, double price, String currency) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.durationInMinute = durationInMinute;
        this.price = price;
        this.currency = currency;
    }

    public String getName() {
        return name;
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
        return Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(description, that.description) && Objects.equals(durationInMinute, that.durationInMinute) && Objects.equals(price, that.price) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, description, durationInMinute, price, currency);
    }

    @Override
    public String toString() {
        return "ServiceDto(" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", durationInMinute=" + durationInMinute +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ')';
    }
}
