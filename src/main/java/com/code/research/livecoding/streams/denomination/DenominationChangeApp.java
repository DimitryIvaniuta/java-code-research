package com.code.research.livecoding.streams.denomination;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class DenominationChangeApp {
    public static void main(String[] args) {
        int priceCents = 154;  // or use BigDecimal for cents/dollars
        int paidCents  = 200;

        BigDecimal price = BigDecimal.valueOf(priceCents);
        BigDecimal paid  = BigDecimal.valueOf(paidCents);
        BigDecimal changeAmt = paid.subtract(price);

        DenominationChangeCalculator calculator = new DenominationChangeCalculator();
        DenominationChange change = calculator.calculateChange(changeAmt);

        log.info("Change amount: ${}", changeAmt);
        log.info("Breakdown (counts):{}", DenominationChangeFormatter.formatWithCounts(change));
        log.info("Breakdown (expanded):{}", DenominationChangeFormatter.formatFlatList(change));

        // Sample output:
        // Change amount: $46
        // Breakdown (counts): $20 x2 + $5 x1 + $1 x1
        // Breakdown (expanded): $20 + $20 + $5 + $1
    }
}
