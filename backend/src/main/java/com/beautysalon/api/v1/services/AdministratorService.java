package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.Administrator;
import com.beautysalon.api.v1.repository.AdministratorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService {
    private AdministratorRepository administratorRepository;

    public AdministratorService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    public List<Administrator> getAll() {
        return administratorRepository.findAll();
    }
}
