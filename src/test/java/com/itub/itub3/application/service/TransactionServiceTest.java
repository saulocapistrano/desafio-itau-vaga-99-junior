package com.itub.itub3.application.service;

import com.itub.itub3.application.dto.request.TransactionRequestDTO;
import com.itub.itub3.domain.exception.IllegalTransactionException;
import com.itub.itub3.domain.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService();
    }

    @Test
    void shouldRegisterValidTransaction() {
        TransactionRequestDTO request = new TransactionRequestDTO(new BigDecimal("100.00"), OffsetDateTime.now().minusSeconds(10));

        Transaction transaction = transactionService.registerTransaction(request);

        assertEquals(request.getValue(), transaction.getValue());
        assertEquals(request.getDateTimeStamp(), transaction.getDateTimeStamp());
    }

    @Test
    void shouldThrowExceptionForNegativeValue() {
        TransactionRequestDTO request = new TransactionRequestDTO(new BigDecimal("-50.00"), OffsetDateTime.now().minusSeconds(10));

        assertThrows(IllegalTransactionException.class, () -> transactionService.registerTransaction(request));
    }

    @Test
    void shouldThrowExceptionForFutureTransaction() {
        TransactionRequestDTO request = new TransactionRequestDTO(new BigDecimal("50.00"), OffsetDateTime.now().plusSeconds(10));

        assertThrows(IllegalTransactionException.class, () -> transactionService.registerTransaction(request));
    }
}
