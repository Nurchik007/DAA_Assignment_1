package com.example;

import java.util.Arrays;

public class DeterministicSelector {

    public static int select(int[] a, int k, AlgorithmMetrics metrics) {
        if (a == null || a.length == 0 || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Invalid input array or index k");
        }

        metrics.reset();
        long startTime = System.nanoTime();

        // Работаем со копией массива, чтобы не менять исходный
        int[] copy = a.clone();
        int result = select(copy, 0, copy.length - 1, k, 1, metrics);

        metrics.executionTimeNs = System.nanoTime() - startTime;
        return result;
    }

    private static int select(int[] a, int low, int high, int k, int depth, AlgorithmMetrics metrics) {
        metrics.updateDepth(depth);

        if (low == high) {
            return a[low];
        }

        // Выбираем опорный элемент с помощью медианы медиан
        int pivotIndex = pivot(a, low, high, depth, metrics);
        pivotIndex = partition(a, low, high, pivotIndex, metrics);

        if (k == pivotIndex) {
            return a[k];
        } else if (k < pivotIndex) {
            return select(a, low, pivotIndex - 1, k, depth + 1, metrics);
        } else {
            return select(a, pivotIndex + 1, high, k, depth + 1, metrics);
        }
    }

    private static int pivot(int[] a, int low, int high, int depth, AlgorithmMetrics metrics) {
        int n = high - low + 1;
        if (n <= 5) {
            return medianOfFive(a, low, high, metrics);
        }

        // Разбиваем на группы по 5 элементов и перемещаем их медианы в начало
        for (int i = 0; i < n / 5; i++) {
            int subLow = low + i * 5;
            int subHigh = subLow + 4;
            int medianIndex = medianOfFive(a, subLow, subHigh, metrics);
            swap(a, low + i, medianIndex, metrics);
        }

        // Рекурсивно находим медиану медиан
        int mid = low + (n / 5) / 2;
        select(a, low, low + n / 5 - 1, mid, depth + 1, metrics);
        return mid;
    }

    private static int medianOfFive(int[] a, int low, int high, AlgorithmMetrics metrics) {
        int length = high - low + 1;
        int[] temp = new int[length];
        System.arraycopy(a, low, temp, 0, length);
        Arrays.sort(temp); // Сортируем маленькую группу до 5 элементов

        for (int i = low; i <= high; i++) {
            metrics.comparisons++;
            if (a[i] == temp[length / 2]) {
                return i;
            }
        }
        return low;
    }

    private static int partition(int[] a, int low, int high, int pivotIndex, AlgorithmMetrics metrics) {
        int pivotValue = a[pivotIndex];
        swap(a, pivotIndex, high, metrics);
        int storeIndex = low;

        for (int i = low; i < high; i++) {
            metrics.comparisons++;
            if (a[i] < pivotValue) {
                swap(a, storeIndex, i, metrics);
                storeIndex++;
            }
        }
        swap(a, storeIndex, high, metrics);
        return storeIndex;
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