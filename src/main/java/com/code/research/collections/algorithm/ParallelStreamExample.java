package com.code.research.collections.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.*;

@Slf4j
public class ParallelStreamExample {

    public static void main(String[] args) {
        List<Integer> numbers = IntStream.rangeClosed(1, 1_000_000).boxed().toList();

        // Sum the numbers using a parallel stream
        long start = System.currentTimeMillis();

        int sum = numbers.parallelStream()
                .reduce(0, Integer::sum);

        long duration = System.currentTimeMillis() - start;

        log.info("Sum: {}", sum);
        log.info("Parallel stream duration: {} ms", duration);
    }

}
