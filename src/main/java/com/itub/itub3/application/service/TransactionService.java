package com.itub.itub3.application.service;

import com.itub.itub3.application.dto.request.TransactionRequestDTO;
import com.itub.itub3.application.dto.response.StatisticsResponseDTO;
import com.itub.itub3.domain.model.Transaction;
import com.itub.itub3.domain.exception.IllegalTransactionException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final CopyOnWriteArrayList<Transaction> transactions = new CopyOnWriteArrayList<>();

    public Transaction registerTransaction(TransactionRequestDTO requestDTO) {
        validateTransaction(requestDTO);

        Transaction transaction = new Transaction(requestDTO.getValue(), requestDTO.getDateTime());
        transactions.add(transaction);
        return transaction;
    }

    public void deleteTransaction() {
        transactions.clear();
    }

    public StatisticsResponseDTO getStatistics() {
        OffsetDateTime sixtySecondsAgo = OffsetDateTime.now().minusSeconds(60);

        List<Transaction> recentTransactions = transactions.stream()
                .filter(transaction -> transaction.getDateTime().isAfter(sixtySecondsAgo))
                .collect(Collectors.toList());

        if (recentTransactions.isEmpty()) {
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

        BigDecimal avg = sum.divide(BigDecimal.valueOf(recentTransactions.size()), BigDecimal.ROUND_HALF_UP);

        return new StatisticsResponseDTO(recentTransactions.size(), sum, avg, min, max);
    }

    private void validateTransaction(TransactionRequestDTO requestDTO) {
        if (requestDTO.getDateTime().isAfter(OffsetDateTime.now())) {
            throw new IllegalTransactionException("Transaction date must not be in the future.");
        }
    }
}
