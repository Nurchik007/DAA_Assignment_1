package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClosestPairTest {
    private AlgorithmMetrics metrics;

    @BeforeEach
    public void setUp() {
        metrics = new AlgorithmMetrics();
    }

    @Test
    public void testCompareWithBruteForce() {
        Random rand = new Random(42);
        int n = 500; // n <= 2000 по требованию задания
        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            points[i] = new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
        }

        double bruteForceResult = ClosestPairSolver.bruteForce(points);
        double fastResult = ClosestPairSolver.findClosestPair(points, metrics);

        assertEquals(bruteForceResult, fastResult, 1e-9, "Divide-and-Conquer result must match Brute-Force");
        assertTrue(metrics.maxRecursionDepth > 0);
    }

    @Test
    public void testSmallDataset() {
        Point[] points = {
                new Point(2, 3),
                new Point(12, 30),
                new Point(40, 50),
                new Point(5, 1),
                new Point(12, 10),
                new Point(3, 4)
        };

        double bruteForceResult = ClosestPairSolver.bruteForce(points);
        double fastResult = ClosestPairSolver.findClosestPair(points, metrics);

        assertEquals(bruteForceResult, fastResult, 1e-9);
    }
}