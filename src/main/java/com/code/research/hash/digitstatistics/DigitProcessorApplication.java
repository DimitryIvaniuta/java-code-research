package com.code.research.hash.digitstatistics;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DigitProcessorApplication {

    public static void main(String[] args) {
        Long inputNumber = 223287543223423L;

        // Using Integer counts.
        DigitStatisticsRecord<Integer> statisticsRecord = DigitProcessor.computeDigits(inputNumber, 1, Integer::sum);
        log.info("Frequency Map (Integer): sum: {} frequency: {}", statisticsRecord.sum(), statisticsRecord.frequencyMap());

        // Using Long counts.
        DigitStatisticsRecord<Long> statisticsRecordLong = DigitProcessor.computeDigits(inputNumber, 1L, Long::sum);
        log.info("Frequency Map (Long): sum: {} frequency: {}", statisticsRecordLong.sum(), statisticsRecordLong.frequencyMap());

        // Using Float counts.
        DigitStatisticsRecord<Float> statisticsRecordFloat = DigitProcessor.computeDigits(inputNumber, 1f, Float::sum);
        log.info("Frequency Map (Float): sum: {} frequency: {}", statisticsRecordFloat.sum(), statisticsRecordFloat.frequencyMap());

        // Using Double counts.
        DigitStatisticsRecord<Double> statisticsRecordDouble = DigitProcessor.computeDigits(inputNumber, 1d, Double::sum);
        log.info("Frequency Map (Double): sum: {} frequency: {}", statisticsRecordDouble.sum(), statisticsRecordDouble.frequencyMap());

        // Compute frequency map with Integer counts.
        DigitStatisticsRecord<Integer> statsInteger =
                DigitProcessor.computeDigitsWithCollector(inputNumber, FrequencyCollector.toFrequencyMapInteger());
        log.info("Digit Statistics (Integer counts): " + statsInteger);

        // 

        // Compute frequency map with Long counts.
        DigitStatisticsRecord<Long> statsLong =
                DigitProcessor.computeDigitsWithCollector(inputNumber, FrequencyCollector.toFrequencyMapLong());
        log.info("Digit Statistics (Long counts): " + statsLong);

        // Compute frequency map with Float counts.
        DigitStatisticsRecord<Float> statsFloat =
                DigitProcessor.computeDigitsWithCollector(inputNumber, FrequencyCollector.toFrequencyMapFloat());
        log.info("Digit Statistics (Float counts): " + statsFloat);

        // Compute frequency map with Double counts.
        DigitStatisticsRecord<Double> statsDouble =
                DigitProcessor.computeDigitsWithCollector(inputNumber, FrequencyCollector.toFrequencyMapDouble());
        log.info("Digit Statistics (Double counts): " + statsDouble);

        //


        // Compute frequency map with Integer counts.
        DigitStatisticsRecord<Integer> statsIntegerConverter =
                DigitProcessor.computeDigitsWithConverter(inputNumber, l -> (int) l);
        log.info("Digit Statistics (Integer counts): " + statsIntegerConverter);

        // Compute frequency map with Long counts.
        DigitStatisticsRecord<Long> statsLongConverter =
                DigitProcessor.computeDigitsWithConverter(inputNumber, l -> l);
        log.info("Digit Statistics (Long counts): " + statsLongConverter);

        // Compute frequency map with Float counts.
        DigitStatisticsRecord<Float> statsFloatConverter =
                DigitProcessor.computeDigitsWithConverter(inputNumber, l -> (float) l);
        log.info("Digit Statistics (Float counts): " + statsFloatConverter);

        // Compute frequency map with Double counts.
        DigitStatisticsRecord<Double> statsDoubleConverter =
                DigitProcessor.computeDigitsWithConverter(inputNumber, l -> (double) l);
        log.info("Digit Statistics (Double counts): " + statsDoubleConverter);

        //
        // Using Integer counts.
        Map<Integer, Integer> freqMapInteger = DigitProcessor.computeFrequencyMap(inputNumber, 1, Integer::sum);
        log.info("Frequency Map (Integer): {}", freqMapInteger);

        // Using Long counts.
        Map<Integer, Long> freqMapLong = DigitProcessor.computeFrequencyMap(inputNumber, 1L, Long::sum);
        log.info("Frequency Map (Long): {}", freqMapLong);

        // Using Float counts.
        Map<Integer, Float> freqMapFloat = DigitProcessor.computeFrequencyMap(inputNumber, 1f, Float::sum);
        log.info("Frequency Map (Float): {}", freqMapFloat);

        // Using Double counts.
        Map<Integer, Double> freqMapDouble = DigitProcessor.computeFrequencyMap(inputNumber, 1d, Double::sum);
        log.info("Frequency Map (Double): {}", freqMapDouble);
        //
        // Compute frequency map with Integer counts.
        DigitStatisticsRecord<Integer> statsIntegerIterator =
                DigitProcessor.computeDigitsIterator(inputNumber, 1, Integer::sum);
        log.info("Digit Statistics (Integer counts): {}", statsIntegerIterator);

        // Compute frequency map with Long counts.
        DigitStatisticsRecord<Long> statsLongIterator =
                DigitProcessor.computeDigitsIterator(inputNumber, 1L, Long::sum);
        log.info("Digit Statistics (Long counts): {}", statsLongIterator);

        // Compute frequency map with Float counts.
        DigitStatisticsRecord<Float> statsFloatIterator =
                DigitProcessor.computeDigitsIterator(inputNumber, 1f, Float::sum);
        log.info("Digit Statistics (Float counts): {}", statsFloatIterator);

        // Compute frequency map with Double counts.
        DigitStatisticsRecord<Double> statsDoubleIterator =
                DigitProcessor.computeDigitsIterator(inputNumber, 1d, Double::sum);
        log.info("Digit Statistics (Double counts): {}", statsDoubleIterator);

    }

}
