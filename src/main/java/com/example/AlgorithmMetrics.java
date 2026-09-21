package com.example;

public class AlgorithmMetrics {
    public long executionTimeNs = 0;
    public int maxRecursionDepth = 0;
    public long comparisons = 0;
    public long swapsOrAllocations = 0;

    public void reset() {
        executionTimeNs = 0;
        maxRecursionDepth = 0;
        comparisons = 0;
        swapsOrAllocations = 0;
    }

    public void updateDepth(int currentDepth) {
        if (currentDepth > maxRecursionDepth) {
            maxRecursionDepth = currentDepth;
        }
    }
}