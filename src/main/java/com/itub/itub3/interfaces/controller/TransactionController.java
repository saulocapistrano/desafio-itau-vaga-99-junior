package com.itub.itub3.interfaces.controller;

import com.itub.itub3.application.dto.request.TransactionRequestDTO;
import com.itub.itub3.application.service.TransactionService;
import com.itub.itub3.interfaces.controller.api.TransactionApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController implements TransactionApi {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public ResponseEntity<Void> createTransaction(TransactionRequestDTO transactionRequestDTO) {
        transactionService.registerTransaction(transactionRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}