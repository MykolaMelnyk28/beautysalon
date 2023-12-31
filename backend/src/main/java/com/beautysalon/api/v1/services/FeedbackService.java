package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.Client;
import com.beautysalon.api.v1.entities.Feedback;
import com.beautysalon.api.v1.exceptions.ResourceNotFoundException;
import com.beautysalon.api.v1.repository.ClientRepository;
import com.beautysalon.api.v1.repository.FeedbackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ClientService clientService;

    public FeedbackService(
            FeedbackRepository feedbackRepository,
            ClientService clientService
    ) {
        this.feedbackRepository = feedbackRepository;
        this.clientService = clientService;
    }

    public Feedback create(Feedback feedback) throws ResourceNotFoundException {
        Client foundClient = clientService.getByEmailOrThrow(feedback.getAuthor().getEmail());
        feedback.setAuthor(foundClient);
        return feedbackRepository.save(feedback);
    }

    public Page<Feedback> getAll(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }

    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
    }

}
