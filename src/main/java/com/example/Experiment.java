package com.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Experiment {
    private static final Random random = new Random(42);

    public static void runExperiments() {
        File resultsDir = new File("results");
        if (!resultsDir.exists()) {
            resultsDir.mkdirs();
        }

        File csvFile = new File(resultsDir, "results.csv");

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            // Заголовок CSV файла
            writer.println("Algorithm,InputType,Size,ExecutionTimeNs,MaxRecursionDepth,Comparisons,SwapsOrAllocations");

            int[] sizes = {100, 1000, 5000, 10000, 20000};
            String[] inputTypes = {"Random", "Sorted", "Reverse-Sorted", "Duplicate-Heavy"};

            for (int size : sizes) {
                for (String inputType : inputTypes) {
                    int[] array = generateArray(size, inputType);

                    // 1. MergeSort
                    int[] a1 = array.clone();
                    AlgorithmMetrics m1 = new AlgorithmMetrics();
                    MergeSorter.sort(a1, m1);
                    writer.printf("%s,%s,%d,%d,%d,%d,%d%n",
                            "MergeSort", inputType, size, m1.executionTimeNs, m1.maxRecursionDepth, m1.comparisons, m1.swapsOrAllocations);

                    // 2. QuickSort
                    int[] a2 = array.clone();
                    AlgorithmMetrics m2 = new AlgorithmMetrics();
                    QuickSorter.sort(a2, m2);
                    writer.printf("%s,%s,%d,%d,%d,%d,%d%n",
                            "QuickSort", inputType, size, m2.executionTimeNs, m2.maxRecursionDepth, m2.comparisons, m2.swapsOrAllocations);

                    // 3. Deterministic Select (ищем медиану n/2)
                    int[] a3 = array.clone();
                    AlgorithmMetrics m3 = new AlgorithmMetrics();
                    DeterministicSelector.select(a3, size / 2, m3);
                    writer.printf("%s,%s,%d,%d,%d,%d,%d%n",
                            "DeterministicSelect", inputType, size, m3.executionTimeNs, m3.maxRecursionDepth, m3.comparisons, m3.swapsOrAllocations);

                    // 4. Closest Pair (только для Random типов, генерируем точки)
                    if (inputType.equals("Random")) {
                        Point[] points = generatePoints(size);
                        AlgorithmMetrics m4 = new AlgorithmMetrics();
                        ClosestPairSolver.findClosestPair(points, m4);
                        writer.printf("%s,%s,%d,%d,%d,%d,%d%n",
                                "ClosestPair", inputType, size, m4.executionTimeNs, m4.maxRecursionDepth, m4.comparisons, m4.swapsOrAllocations);
                    }
                }
                System.out.println("Completed experiments for size n = " + size);
            }

            System.out.println("All experiments finished! CSV saved to " + csvFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    private static int[] generateArray(int size, String type) {
        int[] arr = new int[size];
        switch (type) {
            case "Random":
                for (int i = 0; i < size; i++) arr[i] = random.nextInt(100000);
                break;
            case "Sorted":
                for (int i = 0; i < size; i++) arr[i] = i;
                break;
            case "Reverse-Sorted":
                for (int i = 0; i < size; i++) arr[i] = size - i;
                break;
            case "Duplicate-Heavy":
                for (int i = 0; i < size; i++) arr[i] = random.nextInt(5); // Все значения от 0 до 4
                break;
        }
        return arr;
    }

    private static Point[] generatePoints(int size) {
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(random.nextDouble() * 10000, random.nextDouble() * 10000);
        }
        return points;
    }
}