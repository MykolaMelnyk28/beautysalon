package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1._utils.JSON;
import com.beautysalon.api.v1.dto.ClientDto;
import com.beautysalon.api.v1.dto.EmployeeDto;
import com.beautysalon.api.v1.dto.FeedbackDto;
import com.beautysalon.api.v1.dto.mapper.base.AutoMapper;
import com.beautysalon.api.v1.entities.*;
import com.beautysalon.api.v1.services.AdministratorService;
import com.beautysalon.api.v1.services.FeedbackService;
import com.beautysalon.api.v1.services.MasterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedbackController.class)
@ExtendWith(MockitoExtension.class)
class FeedbackControllerTest {

    private static final class Resources {
        final Client client;
        final ClientDto clientDto;
        final UserEntity user;
        final Master master;
        final EmployeeDto masterDto;
        final Feedback feedback;
        final Feedback savedFeedback;
        final FeedbackDto request;
        final FeedbackDto response;


        Resources() {
            client = new Client(
                    1L,
                    "firstname1",
                    "lastname1",
                    "email1@gmail.com",
                    "+380123456789");
            clientDto = new ClientDto(
                    1L,
                    "firstname1",
                    "lastname1",
                    "email1@gmail.com",
                    "+380123456789");
            user = new UserEntity(
                    1L,
                    "master1",
                    "hash_password",
                    true,
                    true,
                    true,
                    true,
                    null,
                    null,
                    null,
                    null,
                    List.of(),
                    1L
            );
            master = new Master(
                    1L,
                    "firstname1",
                    "lastname1",
                    "surname1",
                    "email1@gmail.com",
                    "+380123456789",
                    user,
                    List.of(),
                    List.of(),
                    List.of()
            );
            masterDto = new EmployeeDto(
                    1L,
                    "firstname1",
                    "lastname1",
                    "surname1",
                    "email1@gmail.com",
                    "+380123456789",
                    "MASTER",
                    List.of(),
                    List.of()
            );
            user.setMaster(master);
            feedback = new Feedback(
                    null,
                    client,
                    master,
                    null,
                    null,
                    null,
                    5,
                    "Text 1"
            );
            savedFeedback = new Feedback(
                    1L,
                    client,
                    master,
                    null,
                    null,
                    null,
                    5,
                    "Text 1"
            );
            request = new FeedbackDto(
                    null,
                    clientDto,
                    masterDto,
                    null,
                    5,
                    "Text 1"
            );
            response = new FeedbackDto(
                    1L,
                    clientDto,
                    masterDto,
                    null,
                    5,
                    "Text 1"
            );
        }
    }

    private Resources objects;


    @MockBean
    private AutoMapper<Feedback, FeedbackDto> feedbackMapper;

    @MockBean
    private FeedbackService mockFeedbackService;

    @Autowired
    MockMvc mvc;

    @BeforeEach
    void init() {
        objects = new Resources();
    }

    @Test
    void getAllWithDefaultPaginationParamsShouldReturn200StatusAndFeedbackList() throws Exception {
        final List<Feedback> feedbacks = List.of(objects.feedback);
        final List<FeedbackDto> feedbackDtos = List.of(objects.response);
        final Page<Feedback> page = new PageImpl<>(feedbacks, PageRequest.of(0, 10), feedbacks.size());
        final Page<FeedbackDto> pageDtos = new PageImpl<>(feedbackDtos, PageRequest.of(0, 10), feedbackDtos.size());

        when(mockFeedbackService.getAll(PageRequest.of(0, 10))).thenReturn(page);
        when(feedbackMapper.toDto(feedbacks.get(0))).thenReturn(feedbackDtos.get(0));

        mvc.perform(get("/v1/feedbacks")
                .param("p", "0")
                .param("s", "10")
                        .param("r", "5"))
                    .andExpect(status().isOk())
                .andExpect(content().json(JSON.stringify(pageDtos)));
    }

    @Test
    void createShouldReturn201Status() throws Exception {
        when(feedbackMapper.toEntity(objects.request)).thenReturn(objects.feedback);
        when(mockFeedbackService.create(objects.feedback)).thenReturn(objects.savedFeedback);
        when(feedbackMapper.toDto(objects.savedFeedback)).thenReturn(objects.response);
        mvc.perform(post("/v1/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.stringify(objects.request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/v1/feedbacks/1"))
                .andExpect(content().json(JSON.stringify(objects.response)));
    }

    @Test
    void updateByIdShouldReturn204StatusWithoutBody() throws Exception {
        final Long id = 1L;
        final FeedbackDto request = new FeedbackDto(
                id,
                objects.request.getAuthor(),
                objects.request.getRecipient(),
                objects.request.getDateCreated(),
                objects.request.getRating(),
                "Updated text 1"
        );
        final Feedback source = new Feedback(
                null,
                objects.feedback.getAuthor(),
                objects.feedback.getMaster(),
                objects.feedback.getAdministrator(),
                objects.feedback.getDateCreated(),
                objects.feedback.getDateUpdated(),
                objects.feedback.getRating(),
                "Updated text 1"
        );
        final Feedback updated = new Feedback(
                id,
                objects.feedback.getAuthor(),
                objects.feedback.getMaster(),
                objects.feedback.getAdministrator(),
                objects.feedback.getDateCreated(),
                objects.feedback.getDateUpdated(),
                objects.feedback.getRating(),
                "Updated text 1"
        );
        final FeedbackDto response = new FeedbackDto(
                id,
                objects.request.getAuthor(),
                objects.request.getRecipient(),
                objects.request.getDateCreated(),
                objects.request.getRating(),
                "Updated text 1"
        );

        when(feedbackMapper.toEntity(request)).thenReturn(source);
        when(mockFeedbackService.updateById(id, source)).thenReturn(updated);
        when(feedbackMapper.toDto(updated)).thenReturn(response);

        mvc.perform(put("/v1/feedbacks/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.stringify(request)))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void deleteByIdShouldReturn204Status() throws Exception {
        final Long id = 1L;

        mvc.perform(delete("/v1/feedbacks/{id}", id))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());

        verify(mockFeedbackService, times(1)).deleteById(id);
    }
}