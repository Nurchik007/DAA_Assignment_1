package com.example;

public class MergeSorter {
    private static final int CUTOFF = 15; // Порог для переключения на Insertion Sort

    public static void sort(int[] a, AlgorithmMetrics metrics) {
        if (a == null || a.length <= 1) {
            return;
        }

        metrics.reset();
        long startTime = System.nanoTime();

        // Повторно используемый вспомогательный массив (буфер)
        int[] aux = new int[a.length];
        metrics.swapsOrAllocations += a.length;

        mergeSort(a, aux, 0, a.length - 1, 1, metrics);

        metrics.executionTimeNs = System.nanoTime() - startTime;
    }

    private static void mergeSort(int[] a, int[] aux, int low, int high, int depth, AlgorithmMetrics metrics) {
        metrics.updateDepth(depth);

        // Маленькие подмассивы сортируем через Insertion Sort
        if (high - low + 1 <= CUTOFF) {
            insertionSort(a, low, high, metrics);
            return;
        }

        int mid = low + (high - low) / 2;

        mergeSort(a, aux, low, mid, depth + 1, metrics);
        mergeSort(a, aux, mid + 1, high, depth + 1, metrics);

        // Оптимизация: если массив уже отсортирован на стыке, слияние не нужно
        metrics.comparisons++;
        if (a[mid] <= a[mid + 1]) {
            return;
        }

        merge(a, aux, low, mid, high, metrics);
    }

    private static void merge(int[] a, int[] aux, int low, int mid, int high, AlgorithmMetrics metrics) {
        // Копируем данные во вспомогательный буфер
        for (int k = low; k <= high; k++) {
            aux[k] = a[k];
            metrics.swapsOrAllocations++;
        }

        int i = low;
        int j = mid + 1;

        // Линейное слияние двух половин обратно в основной массив
        for (int k = low; k <= high; k++) {
            metrics.comparisons++;
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > high) {
                a[k] = aux[i++];
            } else {
                metrics.comparisons++; // Дополнительное сравнение элеметов aux
                if (aux[j] < aux[i]) {
                    a[k] = aux[j++];
                } else {
                    a[k] = aux[i++];
                }
            }
            metrics.swapsOrAllocations++;
        }
    }

    private static void insertionSort(int[] a, int low, int high, AlgorithmMetrics metrics) {
        for (int i = low + 1; i <= high; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= low) {
                metrics.comparisons++;
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    metrics.swapsOrAllocations++;
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
            metrics.swapsOrAllocations++;
        }
    }
}