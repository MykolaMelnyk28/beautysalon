package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1.dto.ClientDto;
import com.beautysalon.api.v1.dto.mapper.base.AutoMapper;
import com.beautysalon.api.v1.entities.Client;
import com.beautysalon.api.v1.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/clients")
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

    @GetMapping("/{email}")
    public ResponseEntity<ClientDto> getByEmail(
            @PathVariable String email
    ) {
        Optional<Client> client = clientService.getByEmail(email);
        return ResponseEntity.of(client.map(clientMapper::toDto));
    }

}
