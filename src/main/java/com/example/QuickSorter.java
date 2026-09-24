package com.example;

import java.util.Random;

public class QuickSorter {
    private static final Random random = new Random();

    public static void sort(int[] a, AlgorithmMetrics metrics) {
        if (a == null || a.length <= 1) {
            return;
        }

        metrics.reset();
        long startTime = System.nanoTime();

        quickSort(a, 0, a.length - 1, 1, metrics);

        metrics.executionTimeNs = System.nanoTime() - startTime;
    }

    private static void quickSort(int[] a, int low, int high, int depth, AlgorithmMetrics metrics) {
        metrics.updateDepth(depth);

        while (low < high) {
            int pivotIndex = partition(a, low, high, metrics);

            if (pivotIndex - low < high - pivotIndex) {
                quickSort(a, low, pivotIndex - 1, depth + 1, metrics);
                low = pivotIndex + 1;
            } else {
                quickSort(a, pivotIndex + 1, high, depth + 1, metrics);
                high = pivotIndex - 1;
            }
        }
    }

    private static int partition(int[] a, int low, int high, AlgorithmMetrics metrics) {
        int randomIndex = low + random.nextInt(high - low + 1);
        swap(a, randomIndex, high, metrics);

        int pivot = a[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            metrics.comparisons++;
            if (a[j] <= pivot) {
                i++;
                swap(a, i, j, metrics);
            }
        }
        swap(a, i + 1, high, metrics);
        return i + 1;
    }

    private static void swap(int[] a, int i, int j, AlgorithmMetrics metrics) {
        if (i != j) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
            metrics.swapsOrAllocations++;
        }
    }
}