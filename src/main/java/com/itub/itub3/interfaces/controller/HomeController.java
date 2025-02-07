package com.itub.itub3.interfaces.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@Tag(name = "Home", description = "Root endpoint for API health check")
public class HomeController {

    @Operation(
            summary = "Check API status",
            description = "Returns a message indicating that the API is up and running."
    )
    @ApiResponse(
            responseCode = "200",
            description = "API is running successfully",
            content = @Content(schema = @Schema(implementation = Map.class))
    )
    @GetMapping("/")
    public ResponseEntity<Map<String, String>> home() {
        return ResponseEntity.ok(Map.of("message", "API Itub3 is running successfully!"));
    }
}