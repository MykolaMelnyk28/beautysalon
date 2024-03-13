package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1.dto.ServiceDto;
import com.beautysalon.api.v1.dto.mapper.base.AutoMapper;
import com.beautysalon.api.v1.entities.ServiceEntity;
import com.beautysalon.api.v1.services.ServiceEntityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServiceController.class)
@ExtendWith(MockitoExtension.class)
class ServiceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServiceEntityService seService;

    @MockBean
    private AutoMapper<ServiceEntity, ServiceDto> mapper;

    @Test
    void createShouldReturn201StatusAndCreatedServiceEntity() throws Exception {
        ServiceDto request = new ServiceDto(
                null,
                "service1",
                "category1",
                "desc1",
                30,
                50.0,
                "UAH"
        );
        ServiceEntity entity = new ServiceEntity(
                null,
                "service1",
                "category1",
                "desc1",
                30,
                50.0,
                "UAH"
        );
        ServiceEntity savedEntity = new ServiceEntity(
                1L,
                "service1",
                "category1",
                "desc1",
                30,
                50.0,
                "UAH"
        );
        ServiceDto response = new ServiceDto(
                1L,
                "service1",
                "category1",
                "desc1",
                30,
                50.0,
                "UAH"
        );

        when(mapper.toEntity(request)).thenReturn(entity);
        when(seService.create(entity)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(response);

        mvc.perform(post("/v1/services")
                        .content(stringify(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/v1/services/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(response));
    }

    @Test
    void getServicesShouldReturn200StatusAndServicesList() throws Exception {
        var services = List.of(
                new ServiceEntity(
                        1L,
                        "service1",
                        "category1",
                        "desc1",
                        30,
                        50.0,
                        "UAH"
                ),
                new ServiceEntity(
                        2L,
                        "service2",
                        "category1.category1_1",
                        "desc2",
                        30,
                        60.0,
                        "UAH"
                ),
                new ServiceEntity(
                        3L,
                        "service3",
                        "category2",
                        "desc3",
                        30,
                        60.0,
                        "UAH"
                )
        );

        var expected = services.stream().map(mapper::toDto).toList();

        when(seService.getAllServices()).thenReturn(services);
        when(mapper.toDto(any(ServiceEntity.class))).thenReturn(expected.get(0), expected.get(1), expected.get(2));

        this.mvc.perform(get("/v1/services"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(stringify(expected)));
    }

    @Test
    void getServiceByIdShouldReturn200StatusAndFoundServiceEntity()
            throws Exception {
        Long id = 1L;

        final ServiceEntity found = new ServiceEntity(
                1L,
                "service1",
                "category1",
                "desc1",
                30,
                50.0,
                "UAH"
        );

        final ServiceDto response = new ServiceDto(
                1L,
                "service1",
                "category1",
                "desc1",
                30,
                50.0,
                "UAH"
        );

        when(seService.getById(id)).thenReturn(Optional.of(found));
        when(mapper.toDto(found)).thenReturn(response);

        mvc.perform(get("/v1/services/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(stringify(response)));
    }

    @Test
    void updateServiceShouldReturn204StatusAndUpdatedServiceEntity() throws Exception {
        Long id = 1L;

        ServiceDto request = new ServiceDto(
                null,
                "service1",
                "category1",
                "desc1",
                30,
                50.0,
                "UAH"
        );
        ServiceEntity entity = new ServiceEntity(
                null,
                "service1",
                "category1",
                "desc1",
                30,
                50.0,
                "UAH"
        );
        ServiceEntity updated = new ServiceEntity(
                1L,
                "service1",
                "category1",
                "desc1",
                30,
                50.0,
                "UAH"
        );
        ServiceDto response = new ServiceDto(
                1L,
                "service1",
                "category1",
                "desc1",
                30,
                50.0,
                "UAH"
        );

        when(mapper.toEntity(request)).thenReturn(entity);
        when(seService.updateService(id, entity)).thenReturn(updated);
        when(mapper.toDto(updated)).thenReturn(response);

        mvc.perform(put("/v1/services/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(stringify(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(response));
    }

    @Test
    void deleteByIdShouldReturn204Status() throws Exception {
        Long id = 1L;

        mvc.perform(delete("/v1/services/{id}", id))
                .andExpect(status().isNoContent());

        verify(seService, times(1)).deleteById(1L);
    }

    static String stringify(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}