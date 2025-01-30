package com.itub.itub3.domain.exception;

public class IllegalTransactionException extends RuntimeException {
    public IllegalTransactionException(String message) {
        super(message);
    }
}