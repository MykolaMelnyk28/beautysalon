package com.beautysalon.domain.entities;

import jakarta.persistence.*;

import java.util.List;

//@Entity
//@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<ServiceEntity> services;

}