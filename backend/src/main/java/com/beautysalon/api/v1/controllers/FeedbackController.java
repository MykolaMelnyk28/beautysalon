package com.beautysalon.api.v1.controllers;

import com.beautysalon.api.v1.dto.FeedbackDto;
import com.beautysalon.api.v1.dto.mapper.base.AutoMapper;
import com.beautysalon.api.v1.entities.Feedback;
import com.beautysalon.api.v1.services.FeedbackService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(feedbackMapper.toDto(created));
    }

    @GetMapping
    public ResponseEntity<Page<FeedbackDto>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", defaultValue = "10") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Feedback> page = feedbackService.getAll(pageable);
        return ResponseEntity.ok(page.map(feedbackMapper::toDto));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteById(
            @PathVariable Long id
    ) {
        feedbackService.deleteById(id);
        return HttpStatus.OK;
    }

}
