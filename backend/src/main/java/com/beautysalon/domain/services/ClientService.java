package com.beautysalon.domain.services;

import com.beautysalon.domain.entities.Client;
import com.beautysalon.domain.repository.ClientRepository;
import com.beautysalon.api.v1.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Example;
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

    public Optional<Client> getById(Long id) {
        return clientRepository.findById(id);
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public List<Client> getAll(boolean isBlocked) {
        Client client = new Client();
        client.setBlocked(isBlocked);
        return clientRepository.findAll(Example.of(client));
    }

    Client getByEmailOrThrow(String email) {
        Optional<Client> clientOpt = getByEmail(email);
        return clientOpt.orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    public void setBlock(Long id, boolean isBlock) {
        block0(getByIdOrThrow(id), isBlock);
    }

    public void setBlock(String email, boolean isBlock) {
        block0(getByEmailOrThrow(email), isBlock);
    }

    private void block0(Client client, boolean isBlock) {
        if (client.isBlocked() == isBlock) return;
        client.setBlocked(isBlock);
        clientRepository.save(client);
    }


    Client getByIdOrThrow(Long id) {
        return getById(id).orElseThrow(() ->
                new ResourceNotFoundException("Client not found"));
    }
}
