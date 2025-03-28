package com.code.research.datastructures.algorithm.divide;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class PointApplication {

    /**
     * Main method demonstrating the usage of the ClosestPair algorithm.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        List<Point> points = Arrays.asList(
                new Point(2.1, 3.5),
                new Point(12.3, 30.1),
                new Point(40.0, 50.0),
                new Point(5.0, 1.0),
                new Point(12.0, 10.3),
                new Point(3.0, 4.0)
        );

        Pair closest = ClosestPair.findClosestPair(points);
        log.info("Closest Pair: {}", closest);
    }

}
