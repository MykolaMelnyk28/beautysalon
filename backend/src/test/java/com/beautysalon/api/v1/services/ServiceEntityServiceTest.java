package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.ServiceEntity;
import com.beautysalon.api.v1.repository.ServiceRepository;
import org.assertj.core.api.Condition;
import org.assertj.core.internal.Conditions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ServiceEntityServiceTest {
    @InjectMocks
    ServiceEntityService seService;
    @Mock
    ServiceRepository seRepository;

    static List<ServiceEntity> seList;
    static ServiceEntity seWithoutId;
    static ServiceEntity seWithId;

    @BeforeAll
    static void setup() {
        seList = List.of(
                new ServiceEntity(
                        1L,
                        "service1",
                        "category1",
                        "desc1",
                        30,
                        100.0,
                        "UAH"),
                new ServiceEntity(
                        2L,
                        "service2",
                        "category1.category1_1",
                        "desc2",
                        20,
                        65.0,
                        "UAH"),
                new ServiceEntity(
                        3L,
                        "service3",
                        "category2",
                        "desc3",
                        30,
                        80,
                        "UAH")
        );
        seWithoutId = new ServiceEntity(
                null,
                "service4",
                "category3",
                "desc4",
                60,
                150.0,
                "UAH");
        seWithId = new ServiceEntity(
                4L,
                "service4",
                "category3",
                "desc4",
                60,
                150.0,
                "UAH");
    }

    @BeforeEach
    void init() {
        //when(seRepository.save(any(ServiceEntity.class))).thenReturn(seWithId);
        //when(seRepository.findAll()).thenReturn(seList);
        //when(seRepository.findById(same(1L))).thenReturn(Optional.of(seList.get(0)));
    }

    @Test
    void createShouldReturnSavedServiceEntity() {
        when(seRepository.save(any(ServiceEntity.class))).thenReturn(seWithId);
        ServiceEntity saved = seService.create(seWithoutId);
        assertThat(saved)
                .isNotNull()
                .isEqualTo(seWithId);
    }

    @Test
    void getAllServicesShouldReturnServiceEntityList() {
        List<ServiceEntity> actual = seService.getAllServices();
        assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(seList);
    }

    @Test
    void getByIdShouldReturnNotEmptyServiceEntityOptional() {
        Optional<ServiceEntity> actual = seService.getById(1L);
        assertThat(actual)
                .isNotEmpty()
                .hasValue(seList.get(0));
    }

    @Disabled
    @Test
    void updateServiceShouldReturnUpdatedServiceEntity() {
        // TODO: Write test for 'updateService' method
    }
}