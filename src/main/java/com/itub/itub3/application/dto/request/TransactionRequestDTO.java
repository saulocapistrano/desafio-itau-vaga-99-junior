package com.itub.itub3.application.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {
    @NotNull
    @DecimalMin(value = "0.0", inclusive = true, message = "Needed value must be equal or greater than 0")
    private BigDecimal value;

    @NotNull(message = "Date and time is required")
    private OffsetDateTime dateTime;
    public BigDecimal getValue() {
        return value;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }
}
