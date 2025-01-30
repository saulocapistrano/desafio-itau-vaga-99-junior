package com.itub.itub3.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class Transaction {
    private final BigDecimal value;
    private final OffsetDateTime dateTimeStamp;
}
