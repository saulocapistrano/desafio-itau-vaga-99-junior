package com.itub.itub3.interfaces.controller;

import com.itub.itub3.application.dto.request.TransactionRequestDTO;
import com.itub.itub3.application.dto.response.StatisticsResponseDTO;
import com.itub.itub3.application.service.TransactionService;
import com.itub.itub3.interfaces.controller.api.TransactionApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Transactions", description = "API for managing financial transactions and statistics")
public class TransactionController implements TransactionApi {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    @Operation(summary = "Create a new transaction", description = "Registers a new transaction in the system.")
    @ApiResponse(responseCode = "201", description = "Transaction successfully created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createTransaction(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Transaction request data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TransactionRequestDTO.class))
            )
            @RequestBody TransactionRequestDTO transactionRequestDTO) {
        transactionService.registerTransaction(transactionRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @Operation(summary = "Delete all transactions", description = "Removes all registered transactions from the system.")
    @ApiResponse(responseCode = "200", description = "Transactions successfully deleted")
    @DeleteMapping("/transactions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteTransactions() {
        transactionService.deleteTransaction();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @Operation(summary = "Retrieve transaction statistics", description = "Returns statistics of recorded transactions.")
    @ApiResponse(responseCode = "200", description = "Statistics successfully retrieved",
            content = @Content(schema = @Schema(implementation = StatisticsResponseDTO.class)))
    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponseDTO> getStatistics() {
        StatisticsResponseDTO statisticsResponseDTO = transactionService.getStatistics();
        return ResponseEntity.ok(statisticsResponseDTO);
    }
}
