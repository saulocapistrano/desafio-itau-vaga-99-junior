package com.itub.itub3.interfaces.controller.api;

import com.itub.itub3.application.dto.request.TransactionRequestDTO;
import com.itub.itub3.application.dto.response.StatisticsResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/transaction")
public interface TransactionApi {
    @PostMapping
    ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionRequestDTO requestDTO);

    @DeleteMapping
    ResponseEntity<Void> deleteTransactions();

    @GetMapping("/statistic")
    ResponseEntity<StatisticsResponseDTO> getStatistics();
}