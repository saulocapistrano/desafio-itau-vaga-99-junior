package com.itub.itub3.application.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class TransactionRequestDTO {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true, message = "Needed value must be equal or greater than 0")
    private BigDecimal value;

    @NotNull(message = "Date and time is required")
    private OffsetDateTime dateTime;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public TransactionRequestDTO() {
    }

    public TransactionRequestDTO(BigDecimal value, OffsetDateTime dateTime) {
        this.value = value;
        this.dateTime = dateTime;
    }
}