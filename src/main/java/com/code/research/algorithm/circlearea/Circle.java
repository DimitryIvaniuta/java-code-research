package com.code.research.algorithm.circlearea;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Circle {

    private final Point p1;

    private final Point p2;

    private final double radius;

    public Circle(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.radius = computeRadius();
    }

    /**
     * Compute radius as half the distance between p1 and p2,
     * assuming they lie on the diameter.
     */
    private double computeRadius() {
        double dx = p2.x() - p1.x();
        double dy = p2.y() - p1.y();
        double diameter = Math.hypot(dx, dy);
        return diameter / 2;
    }

    /** Returns the area: π * r² */
    private double computeArea() {
        return Math.PI * radius * radius;
    }

    /** Prints the area to standard output */
    public void printArea() {
        log.info("Circle with diameter endpoints ({}, {}) and ({}, {})", p1.x(), p1.y(), p2.x(), p2.y());
        log.info("Radius: {}", radius);
        log.info("Area: {}", computeArea());

    }
}
