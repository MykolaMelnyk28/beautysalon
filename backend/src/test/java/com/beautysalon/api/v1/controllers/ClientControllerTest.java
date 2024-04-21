package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1._utils.JSON;
import com.beautysalon.api.v1.dto.ClientDto;
import com.beautysalon.api.v1.dto.mapper.base.AutoMapper;
import com.beautysalon.domain.entities.Client;
import com.beautysalon.domain.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    private static List<Client> clientList;
    private static List<ClientDto> dtoList;

    @MockBean
    private AutoMapper<Client, ClientDto> mapper;

    @MockBean
    private ClientService mockClientService;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void init() {
        clientList = List.of(
                new Client(
                        1L,
                        "firstname1",
                        "lastname1",
                        "email1@gmail.com",
                        "+380123456789"),
                new Client(
                        2L,
                        "firstname2",
                        "lastname2",
                        "email2@gmail.com",
                        "+380987654321")
        );
        dtoList = List.of(
                new ClientDto(
                        1L,
                        "firstname1",
                        "lastname1",
                        "email1@gmail.com",
                        "+380123456789"),
                new ClientDto(
                        2L,
                        "firstname2",
                        "lastname2",
                        "email2@gmail.com",
                        "+380987654321")
        );
    }

    @Test
    void getAllShouldReturn200StatusAndClientList() throws Exception {
        when(mockClientService.getAll()).thenReturn(clientList);
        when(mapper.toDto(clientList.get(0))).thenReturn(dtoList.get(0));
        when(mapper.toDto(clientList.get(1))).thenReturn(dtoList.get(1));

        mvc.perform(get("/v1/clients", request()))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON.stringify(dtoList)));
    }

    @Test
    void deleteShouldReturn204() throws Exception {
        final Long id = 1L;
        mvc.perform(delete("/v1/clients/{id}", id))
                .andExpect(status().isNoContent());

        verify(mockClientService, times(1)).deleteById(id);
    }
}