package com.beautysalon.api.v1.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/version")
    public ResponseEntity<String> version() {
        return ResponseEntity.ok("API BeautySalon 1.0.0");
    }

}