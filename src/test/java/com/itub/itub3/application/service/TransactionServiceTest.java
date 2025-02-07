package com.itub.itub3.application.service;

import com.itub.itub3.application.dto.request.TransactionRequestDTO;
import com.itub.itub3.application.dto.response.StatisticsResponseDTO;
import com.itub.itub3.domain.exception.IllegalTransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link TransactionService}.
 * This class validates the correct behavior of transaction-related operations,
 * including adding, deleting, and retrieving statistics of transactions.
 */
public class TransactionServiceTest {

    private TransactionService transactionService;
    private ConfigurationService configurationService;

    /**
     * Initializes a new instance of {@link TransactionService} before each test execution.
     */
    @BeforeEach
    void setUp() {
        configurationService = Mockito.mock(ConfigurationService.class);

        when(configurationService.getWindowSeconds()).thenReturn(60);

        transactionService = new TransactionService(configurationService);
    }


    @Test
    void shouldRegisterValidTransaction() {
        TransactionRequestDTO request = new TransactionRequestDTO(
                new BigDecimal("100.00"),
                OffsetDateTime.now().minusSeconds(10)
        );

        assertDoesNotThrow(() -> transactionService.registerTransaction(request));
    }

    @Test
    void shouldThrowExceptionForNegativeValue() {
        TransactionRequestDTO request = new TransactionRequestDTO(
                new BigDecimal("-50.00"),
                OffsetDateTime.now().minusSeconds(10)
        );

        IllegalTransactionException exception = assertThrows(IllegalTransactionException.class, () -> transactionService.registerTransaction(request));
        assertEquals("Transaction value must be equal or greater than 0.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForFutureTransaction() {
        TransactionRequestDTO request = new TransactionRequestDTO(
                new BigDecimal("50.00"),
                OffsetDateTime.now().plusSeconds(10)
        );

        assertThrows(IllegalTransactionException.class, () -> transactionService.registerTransaction(request));
    }

    @Test
    void shouldDeleteAllTransactions() {
        transactionService.registerTransaction(new TransactionRequestDTO(new BigDecimal("100.00"), OffsetDateTime.now().minusSeconds(10)));
        transactionService.deleteTransaction();
        assertEquals(0, transactionService.getStatistics().getCount());
    }

    @Test
    void shouldReturnZeroStatisticsWhenNoTransactionsExist() {
        StatisticsResponseDTO statistics = transactionService.getStatistics();

        assertEquals(0, statistics.getCount());
        assertEquals(0, statistics.getSum().compareTo(BigDecimal.ZERO));
        assertEquals(0, statistics.getAvg().compareTo(BigDecimal.ZERO));
        assertEquals(0, statistics.getMin().compareTo(BigDecimal.ZERO));
        assertEquals(0, statistics.getMax().compareTo(BigDecimal.ZERO));
    }

    @Test
    void shouldCalculateStatisticsCorrectly() {
        transactionService.registerTransaction(new TransactionRequestDTO(new BigDecimal("100.00"), OffsetDateTime.now().minusSeconds(5)));
        transactionService.registerTransaction(new TransactionRequestDTO(new BigDecimal("50.00"), OffsetDateTime.now().minusSeconds(20)));
        transactionService.registerTransaction(new TransactionRequestDTO(new BigDecimal("150.00"), OffsetDateTime.now().minusSeconds(30)));

        StatisticsResponseDTO statistics = transactionService.getStatistics();

        assertEquals(3, statistics.getCount());
        assertEquals(0, statistics.getSum().compareTo(new BigDecimal("300.00")));
        assertEquals(0, statistics.getAvg().compareTo(new BigDecimal("100.00")));
        assertEquals(0, statistics.getMin().compareTo(new BigDecimal("50.00")));
        assertEquals(0, statistics.getMax().compareTo(new BigDecimal("150.00")));
    }

    @Test
    void shouldIgnoreTransactionsOlderThanWindow() {
        transactionService.registerTransaction(new TransactionRequestDTO(new BigDecimal("200.00"), OffsetDateTime.now().minusSeconds(120))); // Muito antiga
        transactionService.registerTransaction(new TransactionRequestDTO(new BigDecimal("300.00"), OffsetDateTime.now().minusSeconds(50))); // Dentro da janela

        StatisticsResponseDTO statistics = transactionService.getStatistics();

        assertEquals(1, statistics.getCount());
        assertEquals(0, statistics.getSum().compareTo(new BigDecimal("300.00")));
        assertEquals(0, statistics.getAvg().compareTo(new BigDecimal("300.00")));
        assertEquals(0, statistics.getMin().compareTo(new BigDecimal("300.00")));
        assertEquals(0, statistics.getMax().compareTo(new BigDecimal("300.00")));
    }
}