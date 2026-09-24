package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ClosestPairSolver {

    public static double findClosestPair(Point[] points, AlgorithmMetrics metrics) {
        if (points == null || points.length < 2) {
            return Double.POSITIVE_INFINITY;
        }

        metrics.reset();
        long startTime = System.nanoTime();

        // Создаем копии массива и сортируем по X и по Y
        Point[] pointsSortedByX = points.clone();
        Arrays.sort(pointsSortedByX, Comparator.comparingDouble(p -> p.x));
        metrics.swapsOrAllocations += points.length;

        Point[] pointsSortedByY = points.clone();
        Arrays.sort(pointsSortedByY, Comparator.comparingDouble(p -> p.y));
        metrics.swapsOrAllocations += points.length;

        double minDistance = closest(pointsSortedByX, pointsSortedByY, 0, points.length - 1, 1, metrics);

        metrics.executionTimeNs = System.nanoTime() - startTime;
        return minDistance;
    }

    // Наивный алгоритм O(n²) для проверки маленьких выборок и тестирования
    public static double bruteForce(Point[] points) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = points[i].distanceTo(points[j]);
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }

    private static double closest(Point[] pointsByX, Point[] pointsByY, int low, int high, int depth, AlgorithmMetrics metrics) {
        metrics.updateDepth(depth);

        int n = high - low + 1;

        // Для маленьких наборов (<= 3) используем прямой перебор
        if (n <= 3) {
            double min = Double.POSITIVE_INFINITY;
            for (int i = low; i <= high; i++) {
                for (int j = i + 1; j <= high; j++) {
                    metrics.comparisons++;
                    double dist = pointsByX[i].distanceTo(pointsByX[j]);
                    if (dist < min) {
                        min = dist;
                    }
                }
            }
            return min;
        }

        int mid = low + (high - low) / 2;
        Point midPoint = pointsByX[mid];

        // Делим массивы по Y на левую и правую части
        List<Point> leftYList = new ArrayList<>();
        List<Point> rightYList = new ArrayList<>();
        for (Point p : pointsByY) {
            metrics.comparisons++;
            if (p.x <= midPoint.x) {
                leftYList.add(p);
            } else {
                rightYList.add(p);
            }
        }

        Point[] leftByY = leftYList.toArray(new Point[0]);
        Point[] rightByY = rightYList.toArray(new Point[0]);

        double d1 = closest(pointsByX, leftByY, low, mid, depth + 1, metrics);
        double d2 = closest(pointsByX, rightByY, mid + 1, high, depth + 1, metrics);

        double delta = Math.min(d1, d2);

        // Формируем полосу (strip) шириной delta относительно разделяющей линии
        List<Point> strip = new ArrayList<>();
        for (Point p : pointsByY) {
            metrics.comparisons++;
            if (Math.abs(p.x - midPoint.x) < delta) {
                strip.add(p);
            }
        }

        // Проверяем точки в полосе, отсортированной по Y
        return Math.min(delta, stripClosest(strip, delta, metrics));
    }

    private static double stripClosest(List<Point> strip, double delta, AlgorithmMetrics metrics) {
        double min = delta;
        int size = strip.size();

        for (int i = 0; i < size; i++) {
            // Теорема гарантирует, что достаточно проверить не более 7-8 последующих точек
            for (int j = i + 1; j < size && (strip.get(j).y - strip.get(i).y) < min; j++) {
                metrics.comparisons++;
                double dist = strip.get(i).distanceTo(strip.get(j));
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }
}