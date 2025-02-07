package com.itub.itub3.interfaces.controller;

import com.itub.itub3.application.service.ConfigurationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class ConfigurationController {
    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PatchMapping("/statistics-window")
    public ResponseEntity<String> updateStatisticsWindow(@RequestParam int seconds) {
        try {
            configurationService.setWindowSeconds(seconds);
            return ResponseEntity.ok("Statistics window updated to " + seconds + " seconds.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/statistics-window")
    public ResponseEntity<Integer> getStatisticsWindow() {
        return ResponseEntity.ok(configurationService.getWindowSeconds());
    }
}
