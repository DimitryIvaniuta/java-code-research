package com.code.research.algorithm.streams;

import com.code.research.algorithm.streams.dto.Transaction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SumAmountsByCurrency {

    /**
     * Groups the transactions by currency code and sums their amounts.
     *
     * @param transactions the input list of Transaction
     * @return a Map from currency code (e.g. "USD") to total amount in that currency
     */
    public static Map<String, Double> sumAmountsByCurrency(List<Transaction> transactions) {
        return transactions.stream()
                .collect(
                        Collectors.groupingBy(
                                Transaction::getCurrency,
                                Collectors.summingDouble(Transaction::getAmount)
                        )
                );
    }

    public static void main(String[] args) {
        List<Transaction> txs = List.of(
                new Transaction("USD", 120.50),
                new Transaction("EUR", 75.00),
                new Transaction("USD", 30.25),
                new Transaction("GBP", 10.00),
                new Transaction("EUR", 100.00)
        );

        Map<String, Double> totals = sumAmountsByCurrency(txs);
        totals.forEach((currency, sum) ->
                System.out.printf("%s → %.2f%n", currency, sum)
        );
        // prints:
        // USD → 150.75
        // EUR → 175.00
        // GBP → 10.00
    }

}
