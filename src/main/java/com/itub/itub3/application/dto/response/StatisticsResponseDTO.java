package com.itub.itub3.application.dto.response;

import lombok.Getter;

import java.math.BigDecimal;
@Getter
public class StatisticsResponseDTO {
    private long count;
    private BigDecimal sum;
    private BigDecimal avg;
    private BigDecimal min;
    private BigDecimal max;

    public StatisticsResponseDTO(int i, BigDecimal zero, BigDecimal zero1, BigDecimal zero2, BigDecimal zero3) {
    }
}
