package com.itub.itub3.application.service;

import com.itub.itub3.application.dto.request.TransactionRequestDTO;
import com.itub.itub3.domain.model.Transaction;
import com.itub.itub3.domain.exception.IllegalTransactionException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TransactionService {
    private final CopyOnWriteArrayList<Transaction> transactions = new CopyOnWriteArrayList<>();

    public Transaction registerTransaction(TransactionRequestDTO requestDTO) {
        validateTransaction(requestDTO);

        Transaction transaction = new Transaction(requestDTO.getValue(), requestDTO.getDateTimeStamp());
        transactions.add(transaction);
        return transaction;
    }

    private void validateTransaction(TransactionRequestDTO requestDTO) {
        if (requestDTO.getValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalTransactionException("Value cannot be negative.");
        }
        if (requestDTO.getDateTimeStamp().isAfter(OffsetDateTime.now())) {
            throw new IllegalTransactionException("Transaction date must not be in the future.");
        }
    }
}