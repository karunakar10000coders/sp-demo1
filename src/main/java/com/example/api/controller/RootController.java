package com.example.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getApiOverview() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "UP");
        response.put("message", "Spring Boot Basic API Service is running");
        
        Map<String, String> endpoints = new LinkedHashMap<>();
        endpoints.put("posts", "/posts (or /api/posts)");
        endpoints.put("products", "/products (or /api/products)");
        endpoints.put("users", "/users (or /api/users)");
        
        response.put("endpoints", endpoints);
        return ResponseEntity.ok(response);
    }
}
