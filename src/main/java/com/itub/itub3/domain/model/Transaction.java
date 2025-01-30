package com.itub.itub3.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;


public class Transaction {
    private BigDecimal value;
    private OffsetDateTime dateTime;

    public Transaction() {
    }

    public Transaction(BigDecimal value, OffsetDateTime dateTime) {
        this.value = value;
        this.dateTime = dateTime;
    }

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Transaction that)) return false;
        return Objects.equals(getValue(), that.getValue()) && Objects.equals(getDateTime(), that.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getDateTime());
    }
}
