package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1._utils.JSON;
import com.beautysalon.api.v1.dto.EmployeeDto;
import com.beautysalon.api.v1.dto.WorkScheduleDto;
import com.beautysalon.api.v1.dto.mapper.base.AutoMapper;
import com.beautysalon.domain.services.AdministratorService;
import com.beautysalon.domain.services.EmployeeService;
import com.beautysalon.domain.services.MasterService;
import com.beautysalon.domain.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
    static final class Resources {

        final List<Employee> employees;
        final List<EmployeeDto> dtos;
        final WorkSchedule wsh1;
        final WorkScheduleDto wshDto1;
        final WorkSchedule wsh2;
        final WorkScheduleDto wshDto2;

        Resources() {
            wsh1 = new WorkSchedule(1L, DayOfWeek.MONDAY, LocalTime.parse("09:00"), LocalTime.parse("17:00"));
            wsh2 = new WorkSchedule(2L, DayOfWeek.TUESDAY, LocalTime.parse("10:00"), LocalTime.parse("18:00"));
            wshDto1 = new WorkScheduleDto(1, "09:00-17:00");
            wshDto2 = new WorkScheduleDto(2, "10:00-18:00");
            employees = List.of(
                    new Master(
                            1L,
                            "firstname1",
                            "lastname1",
                            "surname1",
                            "email1@gmail.com",
                            "+380123456789",
                            new UserEntity(
                                    1L,
                                    "master1",
                                    "hash_password",
                                    true,
                                    true,
                                    true,
                                    true,
                                    null,
                                    null,
                                    Set.of(),
                                    LocalDateTime.now(),
                                    LocalDateTime.now(),
                                    List.of(),
                                    1L
                            ),
                            List.of(wsh1),
                            List.of(),
                            List.of()
                    ),
                    new Administrator(
                            2L,
                            "firstname1",
                            "lastname1",
                            "surname1",
                            "admin1@gmail.com",
                            "+380987654321",
                            new UserEntity(
                                    2L,
                                    "admin1",
                                    "hash_password",
                                    true,
                                    true,
                                    true,
                                    true,
                                    null,
                                    null,
                                    Set.of(),
                                    null,
                                    null,
                                    List.of(),
                                    1L
                            ),
                            List.of(wsh2),
                            List.of()
                    )
            );
            employees.get(0).getUser().setMaster((Master)employees.get(0));
            employees.get(1).getUser().setAdministrator((Administrator)employees.get(1));

            dtos = List.of(
                    new EmployeeDto(
                            1L,
                            "firstname1",
                            "lastname1",
                            "surname1",
                            "master1",
                            "email1@gmail.com",
                            "+380123456789",
                            EmployeePosition.MASTER.name(),
                            List.of(),
                            List.of(wshDto1)
                    ),
                    new EmployeeDto(
                            1L,
                            "firstname1",
                            "lastname1",
                            "surname1",
                            "admin1",
                            "admin1@gmail.com",
                            "+380987654321",
                            EmployeePosition.ADMINISTRATOR.name(),
                            List.of(),
                            List.of(wshDto2)
                    )
            );
        }
    }

    private Resources objects;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AutoMapper<WorkSchedule, WorkScheduleDto> workScheduleMapper;

    @MockBean
    private MasterService mockMasterService;

    @MockBean
    private AutoMapper<Master, EmployeeDto> mockMasterMapper;

    @MockBean
    private AdministratorService mockAdministratorService;

    @MockBean
    private AutoMapper<Administrator, EmployeeDto> mockAdministratorMapper;

    @MockBean
    private EmployeeService mockEmployeeService;

    @MockBean
    private AutoMapper<Employee, EmployeeDto> mockEmployeeMapper;

    @MockBean
    private AutoMapper<WorkSchedule, WorkScheduleDto> mockWorkScheduleMapper;

    @BeforeEach
    void init() {
        objects = new Resources();
    }

    @Test
    void getAllShouldReturn200StatusAndEmployeesList() throws Exception {
        final List<WorkScheduleDto> wshs = List.of(objects.wshDto1);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        EmployeeFilter employeeFilter = new EmployeeFilter(
                pageable,
                EmployeePosition.ALL.name(),
                wshs.stream().map(workScheduleMapper::toEntity).toList()
        );

        //when(mockWorkScheduleMapper.toEntity(objects.wshDto1)).thenReturn(objects.wsh1);
        //when(mockEmployeeService.getAllEmployees(employeeFilter)).thenReturn(objects.employees);
        //when(mockEmployeeMapper.toDto(objects.employees.get(0))).thenReturn(objects.dtos.get(0));
        //when(mockEmployeeMapper.toDto(objects.employees.get(1))).thenReturn(objects.dtos.get(1));

        mvc.perform(get("/v1/employees")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .param("pos", "all")
                        .param("wsh", JSON.stringify(wshs))
                )
                .andExpect(status().isOk());
    }

}