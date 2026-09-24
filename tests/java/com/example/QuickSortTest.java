package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuickSortTest {
    private AlgorithmMetrics metrics;

    @BeforeEach
    public void setUp() {
        metrics = new AlgorithmMetrics();
    }

    @Test
    public void testRandomArray() {
        Random rand = new Random(42);
        int[] a = new int[1000];
        int[] expected = new int[1000];
        for (int i = 0; i < a.length; i++) {
            a[i] = rand.nextInt(10000);
            expected[i] = a[i];
        }

        Arrays.sort(expected);
        QuickSorter.sort(a, metrics);

        assertArrayEquals(expected, a);
        assertTrue(metrics.maxRecursionDepth > 0);
        assertTrue(metrics.executionTimeNs > 0);
    }

    @Test
    public void testSortedArray() {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        QuickSorter.sort(a, metrics);
        assertArrayEquals(expected, a);
    }

    @Test
    public void testReverseSortedArray() {
        int[] a = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        QuickSorter.sort(a, metrics);
        assertArrayEquals(expected, a);
    }

    @Test
    public void testDuplicates() {
        int[] a = {5, 1, 5, 3, 5, 1, 3, 5};
        int[] expected = {1, 1, 3, 3, 5, 5, 5, 5};

        QuickSorter.sort(a, metrics);
        assertArrayEquals(expected, a);
    }

    @Test
    public void testEdgeCases() {
        int[] empty = {};
        QuickSorter.sort(empty, metrics);
        assertArrayEquals(new int[]{}, empty);

        int[] single = {42};
        QuickSorter.sort(single, metrics);
        assertArrayEquals(new int[]{42}, single);
    }
}