package com.itub.itub3.interfaces.controller;

import com.itub.itub3.application.service.ConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
@Tag(name = "Configuration", description = "API for managing application settings")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Operation(
            summary = "Update the statistics window",
            description = "Sets the duration (in seconds) for the transaction statistics window."
    )
    @ApiResponse(responseCode = "200", description = "Statistics window successfully updated")
    @ApiResponse(responseCode = "400", description = "Invalid value for statistics window")
    @PatchMapping("/statistics-window")
    public ResponseEntity<String> updateStatisticsWindow(@RequestParam int seconds) {
        try {
            configurationService.setWindowSeconds(seconds);
            return ResponseEntity.ok("Statistics window updated to " + seconds + " seconds.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(
            summary = "Retrieve the statistics window",
            description = "Gets the current duration (in seconds) for the transaction statistics window."
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the statistics window",
            content = @Content(schema = @Schema(implementation = Integer.class)))
    @GetMapping("/statistics-window")
    public ResponseEntity<Integer> getStatisticsWindow() {
        return ResponseEntity.ok(configurationService.getWindowSeconds());
    }
}
