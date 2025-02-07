package com.itub.itub3.controller;


import com.itub.itub3.application.service.ConfigurationService;
import com.itub.itub3.interfaces.controller.ConfigurationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConfigurationController.class)
public class ConfigurationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConfigurationService configurationService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ConfigurationController(configurationService)).build();
    }

    @Test
    void shouldUpdateStatisticsWindow() throws Exception {
        mockMvc.perform(patch("/config/statistics-window?seconds=90"))
                .andExpect(status().isOk())
                .andExpect(content().string("Statistics window updated to 90 seconds."));
    }

    @Test
    void shouldRejectInvalidWindowValue() throws Exception {
        mockMvc.perform(patch("/config/statistics-window?seconds=-5"))
                .andExpect(status().isBadRequest());
    }
}
