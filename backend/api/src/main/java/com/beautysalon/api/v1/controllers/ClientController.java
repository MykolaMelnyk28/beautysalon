package com.beautysalon.api.v1.controllers;

import com.beautysalon.core.dto.ClientDto;
import com.beautysalon.core.dto.mapper.base.AutoMapper;
import com.beautysalon.core.entities.Client;
import com.beautysalon.core.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final AutoMapper<Client, ClientDto> clientMapper;
    private final ClientService clientService;

    public ClientController(
            AutoMapper<Client, ClientDto> clientMapper,
            ClientService clientService
    ) {
        this.clientMapper = clientMapper;
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAll() {
        List<Client> clients = clientService.getAll();
        return ResponseEntity.ok(clients.stream()
                        .map(clientMapper::toDto)
                .toList());
    }

    @GetMapping("/{email}")
    public ResponseEntity<ClientDto> getByEmail(
            @PathVariable String email
    ) {
        return ResponseEntity.of(clientService.getByEmail(email)
                .map(clientMapper::toDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id
    ) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
