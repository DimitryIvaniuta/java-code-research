package com.code.research.algorithm.test.streams;

import com.code.research.algorithm.test.dto.Transaction;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.OptionalDouble;

@Slf4j
public class MaxTransactionAmount {

    public static OptionalDouble maxTransactionAmount(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::amount)
                .max();
    }

    public static void main(String[] args) {
        List<Transaction> txs = List.of(
                new Transaction(125.50),
                new Transaction(89.99),
                new Transaction(250.00),
                new Transaction(199.75)
        );

        OptionalDouble maxAmount = maxTransactionAmount(txs);
        if (maxAmount.isPresent()) {
            log.info("Max transaction amount: {}", maxAmount.getAsDouble());
        } else {
            log.info("No transactions available.");
        }
        // Expected output: Max transaction amount: 250.0
    }

}
