package com.code.research.function;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.function.Supplier;

@Slf4j
public class SupplierExample {

    public static void main(String[] args) {
        // Supplier that provides the current date and time.
        Supplier<LocalDateTime> dateTimeSupplier = LocalDateTime::now;
        log.info("Current DateTime: " + dateTimeSupplier.get());
    }

}
