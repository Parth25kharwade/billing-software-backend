package com.parth.billingsoftware.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class HealthController {

    // Accessible at: http://localhost:8080/api/v1.0/health
    @GetMapping("/api/v1.0/health")
    public String healthCheck() {
        return "OK";
    }

}
