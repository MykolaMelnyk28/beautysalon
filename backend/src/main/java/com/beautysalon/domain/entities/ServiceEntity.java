package com.beautysalon.domain.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "services", uniqueConstraints = @UniqueConstraint(
        columnNames = {"name"}
))
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "category_path")
    private String categoryPath = "_";

    @Column(nullable = false)
    private String description = "";

    @Column(nullable = false)
    private int durationInMinute;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String currency;

    public ServiceEntity() {
    }

    public ServiceEntity(Long id, String name, String categoryPath, String description, int durationInMinute, double price, String currency) {
        this.id = id;
        this.name = name;
        this.categoryPath = categoryPath;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
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
        ServiceEntity entity = (ServiceEntity) o;
        return durationInMinute == entity.durationInMinute && Double.compare(price, entity.price) == 0 && Objects.equals(id, entity.id) && Objects.equals(name, entity.name) && Objects.equals(categoryPath, entity.categoryPath) && Objects.equals(description, entity.description) && Objects.equals(currency, entity.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, categoryPath, description, durationInMinute, price, currency);
    }

    @Override
    public String toString() {
        return "ServiceEntity(" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryPath='" + categoryPath + '\'' +
                ", description='" + description + '\'' +
                ", durationInMinute=" + durationInMinute +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ')';
    }
}