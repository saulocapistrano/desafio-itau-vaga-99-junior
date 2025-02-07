package com.itub.itub3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itub.itub3.application.dto.request.TransactionRequestDTO;
import com.itub.itub3.application.dto.response.StatisticsResponseDTO;
import com.itub.itub3.application.service.TransactionService;
import com.itub.itub3.domain.exception.IllegalTransactionException;
import com.itub.itub3.interfaces.controller.TransactionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateTransactionSuccessfully() {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(
                new BigDecimal("100.00"),
                OffsetDateTime.now().minusSeconds(10)
        );

        ResponseEntity<Void> response = transactionController.createTransaction(requestDTO);

        assertEquals(201, response.getStatusCodeValue());
        verify(transactionService, times(1)).registerTransaction(requestDTO);
    }

    @Test
    void shouldReturn422ForNegativeTransactionValue() {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(
                new BigDecimal("-50.00"),
                OffsetDateTime.now().minusSeconds(10)
        );

        doThrow(new IllegalTransactionException("Transaction value cannot be negative"))
                .when(transactionService).registerTransaction(requestDTO);

        assertThrows(IllegalTransactionException.class, () -> transactionController.createTransaction(requestDTO));
        verify(transactionService, times(1)).registerTransaction(requestDTO);
    }

    @Test
    void shouldReturn422ForFutureTransaction() {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(
                new BigDecimal("50.00"),
                OffsetDateTime.now().plusSeconds(10)
        );

        doThrow(new IllegalTransactionException("Transaction date cannot be in the future"))
                .when(transactionService).registerTransaction(requestDTO);

        assertThrows(IllegalTransactionException.class, () -> transactionController.createTransaction(requestDTO));
        verify(transactionService, times(1)).registerTransaction(requestDTO);
    }

    @Test
    void shouldDeleteAllTransactions() {
        ResponseEntity<Void> response = transactionController.deleteTransactions();

        assertEquals(200, response.getStatusCodeValue());
        verify(transactionService, times(1)).deleteTransaction();
    }

    @Test
    void shouldReturnStatisticsSuccessfully() {
        StatisticsResponseDTO statistics = new StatisticsResponseDTO(
                3, new BigDecimal("300.00"), new BigDecimal("100.00"),
                new BigDecimal("50.00"), new BigDecimal("150.00")
        );

        when(transactionService.getStatistics()).thenReturn(statistics);

        ResponseEntity<StatisticsResponseDTO> response = transactionController.getStatistics();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().getCount());
        assertEquals(new BigDecimal("300.00"), response.getBody().getSum());
        assertEquals(new BigDecimal("100.00"), response.getBody().getAvg());
        assertEquals(new BigDecimal("50.00"), response.getBody().getMin());
        assertEquals(new BigDecimal("150.00"), response.getBody().getMax());

        verify(transactionService, times(1)).getStatistics();
    }

    @Test
    void shouldReturnZeroStatisticsWhenNoTransactionsExist() {
        StatisticsResponseDTO emptyStatistics = new StatisticsResponseDTO(
                0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
        );

        when(transactionService.getStatistics()).thenReturn(emptyStatistics);

        ResponseEntity<StatisticsResponseDTO> response = transactionController.getStatistics();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getCount());
        assertEquals(BigDecimal.ZERO, response.getBody().getSum());
        assertEquals(BigDecimal.ZERO, response.getBody().getAvg());
        assertEquals(BigDecimal.ZERO, response.getBody().getMin());
        assertEquals(BigDecimal.ZERO, response.getBody().getMax());

        verify(transactionService, times(1)).getStatistics();
    }
}