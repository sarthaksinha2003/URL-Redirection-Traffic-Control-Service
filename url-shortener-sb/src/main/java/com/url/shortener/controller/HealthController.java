package com.url.shortener.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(
                Map.of(
                        "status", "UP",
                        "message", "Backend API is running successfully",
                        "service", "URL Shortener Backend API",
                        "authentication", "JWT Enabled",
                        "features", "URL Shortening, Redirect, Analytics",
                        "author", "Sarthak Sinha",
                        "github", "https://github.com/your-username/url-shortener-sb",
                        "timestamp", LocalDateTime.now()
                )
        );
    }
}