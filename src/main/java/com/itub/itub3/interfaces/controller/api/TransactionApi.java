package com.itub.itub3.interfaces.controller.api;

import com.itub.itub3.application.dto.request.TransactionRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/transactions")
public interface TransactionApi {
    @PostMapping
    ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionRequestDTO requestDTO);
}