package com.beautysalon.domain.services;

import com.beautysalon.domain.entities.Client;
import com.beautysalon.domain.repository.ClientRepository;
import com.beautysalon.api.v1.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client create(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> getByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    Client getByEmailOrThrow(String email) {
        Optional<Client> clientOpt = getByEmail(email);
        return clientOpt.orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
