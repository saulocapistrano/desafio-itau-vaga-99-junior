package com.itub.itub3.interfaces.controller.api;

import com.itub.itub3.application.dto.request.TransactionRequestDTO;
import com.itub.itub3.application.dto.response.StatisticsResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/transactions")
public interface TransactionApi {
    @PostMapping
    ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionRequestDTO requestDTO);

    @DeleteMapping
    ResponseEntity<Void> deleteTransactions();

    @GetMapping("/statistics")
    ResponseEntity<StatisticsResponseDTO> getStatistics();
}