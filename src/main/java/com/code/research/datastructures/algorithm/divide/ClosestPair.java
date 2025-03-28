package com.code.research.datastructures.algorithm.divide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * ClosestPair implements the divide and conquer algorithm to find the closest pair of points.
 *
 * <p>This algorithm is widely used in applications such as geographic information systems,
 * clustering, and computer graphics.
 */
public class ClosestPair {

    private ClosestPair() {
        //
    }

    /**
     * Returns the Euclidean distance between two points.
     *
     * @param a the first point.
     * @param b the second point.
     * @return the distance between a and b.
     */
    private static double distance(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Finds the closest pair of points in the given list using a divide and conquer approach.
     *
     * @param points the list of points.
     * @return a Pair representing the closest two points and their distance.
     */
    public static Pair findClosestPair(List<Point> points) {

        if (points == null || points.size() < 2) {
            throw new IllegalArgumentException("At least two points are required");
        }

        // Sort points by x-coordinate.
        Point[] pts = points.toArray(new Point[0]);
        Arrays.sort(pts, Comparator.comparingDouble(p -> p.x));

        return findClosestPairRec(pts, 0, pts.length - 1);
    }

    /**
     * Recursive helper method to find the closest pair in the subset of points between indices low and high.
     *
     * @param pts the sorted array of points.
     * @param low the starting index.
     * @param high the ending index.
     * @return the closest pair in the subset.
     */
    private static Pair findClosestPairRec(Point[] pts, int low, int high) {
        // Base case: when only two points, return that pair.
        if (high - low == 1) {
            return new Pair(pts[low], pts[high], distance(pts[low], pts[high]));
        }
        // Base case: when three points, find the closest pair by checking all combinations.
        if (high - low == 2) {
            Pair pair1 = new Pair(pts[low], pts[low + 1], distance(pts[low], pts[low + 1]));
            Pair pair2 = new Pair(pts[low], pts[high], distance(pts[low], pts[high]));
            Pair pair3 = new Pair(pts[low + 1], pts[high], distance(pts[low + 1], pts[high]));
            return minPair(pair1, pair2, pair3);
        }

        // Divide
        int mid = low + (high - low) / 2;
        Point midPoint = pts[mid];

        // Conquer
        Pair leftPair = findClosestPairRec(pts, low, mid);
        Pair rightPair = findClosestPairRec(pts, mid + 1, high);
        Pair bestPair = leftPair.distance < rightPair.distance ? leftPair : rightPair;
        double delta = bestPair.distance;

        // Combine: Build the strip of points that are within delta of the midPoint.x.
        List<Point> strip = new ArrayList<>();
        for (int i = low; i <= high; i++) {
            if (Math.abs(pts[i].x - midPoint.x) < delta) {
                strip.add(pts[i]);
            }
        }
        // Sort the strip by y-coordinate.
        strip.sort(Comparator.comparingDouble(p -> p.y));

        // Check pairs in the strip to find a better pair.
        for (int i = 0; i < strip.size(); i++) {
            // Only need to check next 7 points in strip (a proven property)
            for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < delta; j++) {
                double dist = distance(strip.get(i), strip.get(j));
                if (dist < delta) {
                    delta = dist;
                    bestPair = new Pair(strip.get(i), strip.get(j), dist);
                }
            }
        }
        return bestPair;
    }

    /**
     * Returns the pair with the minimum distance among three pairs.
     *
     * @param p1 the first pair.
     * @param p2 the second pair.
     * @param p3 the third pair.
     * @return the pair with the smallest distance.
     */
    private static Pair minPair(Pair p1, Pair p2, Pair p3) {
        Pair min = p1;
        if (p2.distance < min.distance) {
            min = p2;
        }
        if (p3.distance < min.distance) {
            min = p3;
        }
        return min;
    }

}
