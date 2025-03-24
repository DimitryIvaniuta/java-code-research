package com.code.research.datastructures.arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemperatureLogger {

    public static void main(String[] args) {
        // Array storing temperature readings for a week.
        double[] temperatures = {32.5, 33.0, 31.8, 30.2, 29.9, 28.7, 27.3};

        for (double temp : temperatures) {
            log.info("Temperature: {} *F", temp);
        }
    }

}