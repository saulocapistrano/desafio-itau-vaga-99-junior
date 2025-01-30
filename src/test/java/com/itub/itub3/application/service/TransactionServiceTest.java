package com.itub.itub3.application.service;

import com.itub.itub3.application.dto.request.TransactionRequestDTO;
import com.itub.itub3.application.dto.response.StatisticsResponseDTO;
import com.itub.itub3.domain.exception.IllegalTransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link TransactionService}.
 * This class validates the correct behavior of transaction-related operations,
 * including adding, deleting, and retrieving statistics of transactions.
 */
public class TransactionServiceTest {

    private TransactionService transactionService;

    /**
     * Initializes a new instance of {@link TransactionService} before each test execution.
     */
    @BeforeEach
    void setUp() {
        transactionService = new TransactionService();
    }

    /**
     * Tests if a valid transaction can be registered without throwing any exception.
     * A transaction is considered valid if it has a non-negative value and a timestamp in the past or present.
     */
    @Test
    void shouldRegisterValidTransaction() {
        TransactionRequestDTO request = new TransactionRequestDTO(
                new BigDecimal("100.00"),
                OffsetDateTime.now().minusSeconds(10)
        );

        assertDoesNotThrow(() -> transactionService.registerTransaction(request));
    }

    /**
     * Tests if an exception is thrown when attempting to register a transaction with a negative value.
     * Expected outcome: {@link IllegalTransactionException} should be thrown.
     */
    @Test
    void shouldThrowExceptionForNegativeValue() {
        TransactionRequestDTO request = new TransactionRequestDTO(
                new BigDecimal("-50.00"),
                OffsetDateTime.now().minusSeconds(10)
        );

        assertThrows(IllegalTransactionException.class, () -> transactionService.registerTransaction(request));
    }

    /**
     * Tests if an exception is thrown when attempting to register a transaction with a future timestamp.
     * Expected outcome: {@link IllegalTransactionException} should be thrown.
     */
    @Test
    void shouldThrowExceptionForFutureTransaction() {
        TransactionRequestDTO request = new TransactionRequestDTO(
                new BigDecimal("50.00"),
                OffsetDateTime.now().plusSeconds(10)
        );

        assertThrows(IllegalTransactionException.class, () -> transactionService.registerTransaction(request));
    }

    /**
     * Tests whether all transactions can be successfully deleted.
     * Expected outcome: After deletion, the total count of transactions should be zero.
     */
    @Test
    void shouldDeleteAllTransactions() {
        transactionService.deleteTransaction();
        assertEquals(0, transactionService.getStatistics().getCount());
    }

    /**
     * Tests whether the statistics return zero values when no transactions exist.
     * Expected outcome: All statistical values (count, sum, avg, min, max) should be zero.
     */
    @Test
    void shouldReturnZeroStatisticsWhenNoTransactionsExist() {
        StatisticsResponseDTO statistics = transactionService.getStatistics();

        assertEquals(0, statistics.getCount());
        assertEquals(BigDecimal.ZERO, statistics.getSum());
        assertEquals(BigDecimal.ZERO, statistics.getAvg());
        assertEquals(BigDecimal.ZERO, statistics.getMin());
        assertEquals(BigDecimal.ZERO, statistics.getMax());
    }
}