package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelectTest {
    private AlgorithmMetrics metrics;

    @BeforeEach
    public void setUp() {
        metrics = new AlgorithmMetrics();
    }

    @Test
    public void testAtLeast100RandomRuns() {
        Random rand = new Random(42);

        // Согласно заданию, запускаем минимум 100 случайных тестов
        for (int run = 0; run < 100; run++) {
            int n = rand.nextInt(200) + 1; // размер массива от 1 до 200
            int[] a = new int[n];
            int[] expected = new int[n];

            for (int i = 0; i < n; i++) {
                a[i] = rand.nextInt(1000) - 500;
                expected[i] = a[i];
            }

            Arrays.sort(expected);
            int k = rand.nextInt(n); // случайный k-й элемент

            int result = DeterministicSelector.select(a, k, metrics);

            assertEquals(expected[k], result, "Failed on run " + run + " for k=" + k);
        }
    }
}