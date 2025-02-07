package com.itub.itub3.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {
    private int windowSeconds;

    public ConfigurationService(@Value("${app.statistics.window-seconds}") int windowSeconds) {
        this.windowSeconds = windowSeconds;
    }

    public int getWindowSeconds() {
        return windowSeconds;
    }

    public void setWindowSeconds(int windowSeconds) {
        if (windowSeconds <= 0) {
            throw new IllegalArgumentException("Window seconds must be greater than zero.");
        }
        this.windowSeconds = windowSeconds;
    }
}