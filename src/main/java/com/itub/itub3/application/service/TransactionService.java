package com.itub.itub3.application.service;

import com.itub.itub3.application.dto.request.TransactionRequestDTO;
import com.itub.itub3.application.dto.response.StatisticsResponseDTO;
import com.itub.itub3.domain.exception.IllegalTransactionException;
import com.itub.itub3.domain.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final CopyOnWriteArrayList<Transaction> transactions = new CopyOnWriteArrayList<>();
    private final ConfigurationService configurationService;

    public TransactionService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public Transaction registerTransaction(TransactionRequestDTO requestDTO) {
        logger.info("Received transaction request: {}", requestDTO);
        validateTransaction(requestDTO);

        Transaction transaction = new Transaction(requestDTO.getValue(), requestDTO.getDateTime());
        transactions.add(transaction);

        logger.info("Transaction registered successfully. Total transactions stored: {}", transactions.size());
        return transaction;
    }

    public void deleteTransaction() {
        transactions.clear();
        logger.warn("All transactions have been deleted.");
    }

    public StatisticsResponseDTO getStatistics() {
        int windowSeconds = configurationService.getWindowSeconds();
        OffsetDateTime timeLimit = OffsetDateTime.now().minusSeconds(windowSeconds);

        logger.info("Calculating statistics for transactions after {}", timeLimit);

        List<Transaction> recentTransactions = transactions.stream()
                .filter(transaction -> transaction.getDateTime().isAfter(timeLimit))
                .collect(Collectors.toList());

        if (recentTransactions.isEmpty()) {
            logger.info("No transactions found in the last {} seconds.", windowSeconds);
            return new StatisticsResponseDTO(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        BigDecimal sum = recentTransactions.stream()
                .map(Transaction::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal min = recentTransactions.stream()
                .map(Transaction::getValue)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal max = recentTransactions.stream()
                .map(Transaction::getValue)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal avg = sum.divide(BigDecimal.valueOf(recentTransactions.size()), 2, RoundingMode.HALF_UP);

        logger.info("Statistics calculated: count={}, sum={}, avg={}, min={}, max={}",
                recentTransactions.size(), sum, avg, min, max);

        return new StatisticsResponseDTO(recentTransactions.size(), sum, avg, min, max);
    }

    private void validateTransaction(TransactionRequestDTO requestDTO) {
        if (requestDTO.getDateTime().isAfter(OffsetDateTime.now())) {
            logger.error("Transaction date is in the future: {}", requestDTO.getDateTime());
            throw new IllegalTransactionException("Transaction date must not be in the future.");
        }
    }
}