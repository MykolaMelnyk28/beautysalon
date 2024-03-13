package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.dto.FeedbackDto;
import com.beautysalon.api.v1.dto.mapper.FeedbackMapper;
import com.beautysalon.api.v1.dto.mapper.base.AbstractMapper;
import com.beautysalon.api.v1.dto.mapper.base.AutoMapper;
import com.beautysalon.api.v1.entities.Administrator;
import com.beautysalon.api.v1.entities.Client;
import com.beautysalon.api.v1.entities.Feedback;
import com.beautysalon.api.v1.exceptions.ResourceNotFoundException;
import com.beautysalon.api.v1.repository.ClientRepository;
import com.beautysalon.api.v1.repository.FeedbackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ClientService clientService;
    private final AdministratorService administratorService;
    private final MasterService masterService;
    //private final EmployeeService employeeService;

    public FeedbackService(
            FeedbackRepository feedbackRepository,
            ClientService clientService,
            AdministratorService administratorService,
            MasterService masterService
    ) {
        this.feedbackRepository = feedbackRepository;
        this.clientService = clientService;
        this.masterService = masterService;
        this.administratorService = administratorService;
    }

    public Feedback create(Feedback feedback)
            throws ResourceNotFoundException {
        Optional<Client> foundClient = clientService.getByEmail(feedback.getAuthor().getEmail());
        foundClient.ifPresent(client -> {
            feedback.setAuthor(client);

        });

        return feedbackRepository.save(feedback);
    }

    public Page<Feedback> getAll(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }

    public Feedback updateById(Long id, Feedback feedback) {
        AutoMapper<Feedback, Feedback> mapper = new AbstractMapper<Feedback, Feedback>() {};
        Feedback found = getByIdOrThrow(id);
        mapper.transferEntityDto(feedback, found);
        found.setId(id);
        feedbackRepository.save(found);
        return feedback;
    }

    public Optional<Feedback> getById(Long id) {
        return feedbackRepository.findById(id);
    }

    Feedback getByIdOrThrow(Long id) {
        return getById(id).orElseThrow(() ->
                new ResourceNotFoundException("Feedback not found."));
    }

    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
    }

}
