package com.code.research.hash.digitstatistics;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class FrequencyCollectorApplication {
    public static void main(String[] args) {
        String numberStr = "2232423";

        // Using FrequencyCollector to count frequencies with Integer counts.
        Map<Integer, Integer> freqInteger = numberStr.chars()
                .map(c -> c - '0')
                .boxed()
                .collect(FrequencyCollector.toFrequencyMapInteger());
        log.info("Frequency Map (Integer): " + freqInteger);

        // Using FrequencyCollector to count frequencies with Long counts.
        Map<Integer, Long> freqLong = numberStr.chars()
                .map(c -> c - '0')
                .boxed()
                .collect(FrequencyCollector.toFrequencyMapLong());
        log.info("Frequency Map (Long): " + freqLong);

        // Using FrequencyCollector to count frequencies with Float counts.
        Map<Integer, Float> freqFloat = numberStr.chars()
                .map(c -> c - '0')
                .boxed()
                .collect(FrequencyCollector.toFrequencyMapFloat());
        log.info("Frequency Map (Float): " + freqFloat);

        // Using FrequencyCollector to count frequencies with Double counts.
        Map<Integer, Double> freqDouble = numberStr.chars()
                .map(c -> c - '0')
                .boxed()
                .collect(FrequencyCollector.toFrequencyMapDouble());
        log.info("Frequency Map (Double): " + freqDouble);
    }
    
}
