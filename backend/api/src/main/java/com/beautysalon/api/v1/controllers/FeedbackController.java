package com.beautysalon.api.v1.controllers;

import com.beautysalon.core.dto.FeedbackDto;
import com.beautysalon.core.dto.mapper.base.AutoMapper;
import com.beautysalon.core.dto.validation.OnCreate;
import com.beautysalon.core.dto.validation.OnPut;
import com.beautysalon.core.entities.Feedback;
import com.beautysalon.core.services.FeedbackService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/feedbacks")
public class FeedbackController {

    private final AutoMapper<Feedback, FeedbackDto> feedbackMapper;
    private final FeedbackService feedbackService;

    public FeedbackController(
            AutoMapper<Feedback, FeedbackDto> feedbackMapper,
            FeedbackService feedbackService
    ) {
        this.feedbackMapper = feedbackMapper;
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackDto> create(
            @Validated(OnCreate.class) @RequestBody FeedbackDto request
    ) {
        System.out.println(request);
        Feedback feedback = feedbackMapper.toEntity(request);
        Feedback created = feedbackService.create(feedback);
        FeedbackDto response = feedbackMapper.toDto(created);
        return ResponseEntity.created(URI.create("/v1/feedbacks/" + created.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<FeedbackDto>> getAll(
            Pageable pageable
    ) {
        //Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Feedback> page = feedbackService.getAll(pageable);
        return ResponseEntity.ok(page
                .map(feedbackMapper::toDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(
            @PathVariable Long id,
            @Validated(OnPut.class) @RequestBody FeedbackDto request
    ) {
        Feedback feedback = feedbackMapper.toEntity(request);
        feedbackService.updateById(id, feedback);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {
        feedbackService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
