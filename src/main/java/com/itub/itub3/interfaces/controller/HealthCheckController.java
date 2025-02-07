package com.itub.itub3.interfaces.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/health")
@Tag(name = "Health Check", description = "API for checking system health status")
public class HealthCheckController {

    @Operation(
            summary = "Check API health status",
            description = "Returns a simple message indicating that the API is running."
    )
    @ApiResponse(
            responseCode = "200",
            description = "API is running successfully",
            content = @Content(schema = @Schema(implementation = Map.class))
    )
    @GetMapping
    public ResponseEntity<Map<String, String>> checkHealth() {
        return ResponseEntity.ok(Map.of("status", "API is running successfully!"));
    }
}