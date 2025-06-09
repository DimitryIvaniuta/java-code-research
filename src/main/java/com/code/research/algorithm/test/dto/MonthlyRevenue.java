package com.code.research.algorithm.test.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MonthlyRevenue {
    private final LocalDate month;
    private final long productId;
    private final BigDecimal monthlyRevenue;

    public MonthlyRevenue(LocalDate month, long productId, BigDecimal monthlyRevenue) {
        this.month = month;
        this.productId = productId;
        this.monthlyRevenue = monthlyRevenue;
    }
}
