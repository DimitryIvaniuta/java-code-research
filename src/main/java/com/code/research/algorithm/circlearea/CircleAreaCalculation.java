package com.code.research.algorithm.circlearea;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class CircleAreaCalculation<E> {

    public static void main(String[] args) {
        try {

            double x1 = 1;
            double y1 = 2;
            double x2 = 3;
            double y2 = 4;

            Point p1 = new Point(x1, y1);
            Point p2 = new Point(x2, y2);
            Circle circle = new Circle(p1, p2);
            circle.printArea();
        } catch (NumberFormatException e) {
            log.error("Error: All coordinates must be valid numbers.");
        }
    }
}
