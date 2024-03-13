package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1.dto.FeedbackDto;
import com.beautysalon.api.v1.dto.mapper.base.AutoMapper;
import com.beautysalon.api.v1.entities.Feedback;
import com.beautysalon.api.v1.services.FeedbackService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditor;
import java.net.URI;

@RestController
@RequestMapping("/v1/feedbacks")
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
            @RequestBody FeedbackDto request
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
        return ResponseEntity.ok(page.map(feedbackMapper::toDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(
            @PathVariable Long id,
            @RequestBody FeedbackDto request
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
