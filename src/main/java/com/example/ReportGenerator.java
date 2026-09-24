package com.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReportGenerator {

    public static void main(String[] args) {
        File readmeFile = new File("README.md");

        try (PrintWriter writer = new PrintWriter(new FileWriter(readmeFile))) {
            writer.println("# Assignment 1: Divide-and-Conquer Algorithm Analysis\n");

            writer.println("## A. Project Overview");
            writer.println("This project implements four classic Divide-and-Conquer algorithms in Java:");
            writer.println("1. **MergeSort**: Linear merge, reusable auxiliary buffer, cutoff to Insertion Sort.");
            writer.println("2. **QuickSort**: Randomized pivot selection, in-place partitioning, tail-recursion optimization.");
            writer.println("3. **Deterministic Select**: Median-of-Medians pivot selection, guaranteed linear time.");
            writer.println("4. **Closest Pair of Points**: Divide-and-conquer geometry algorithm on 2D plane.\n");

            writer.println("## B. Algorithm Analysis");
            writer.println("### 1. MergeSort");
            writer.println("- **Recurrence**: $T(n) = 2T(n/2) + \\Theta(n)$");
            writer.println("- **Master Theorem**: Case 2 ($a = 2, b = 2, f(n) = n \\implies \\Theta(n \\log n)$).");
            writer.println("- **Space Complexity**: $\\Theta(n)$ due to auxiliary buffer.");

            writer.println("\n### 2. QuickSort");
            writer.println("- **Recurrence**: $T(n) = T(k) + T(n - k - 1) + \\Theta(n)$");
            writer.println("- **Best/Average Case**: $\\Theta(n \\log n)$");
            writer.println("- **Worst Case**: $O(n^2)$ (mitigated using randomized pivot).");
            writer.println("- **Recursion Depth**: Bound to $O(\\log n)$ by recursing on smaller sub-array first.");

            writer.println("\n### 3. Deterministic Select (Median-of-Medians)");
            writer.println("- **Recurrence**: $T(n) \\le T(n/5) + T(7n/10) + \\Theta(n)$");
            writer.println("- **Analysis**: Since $n/5 + 7n/10 = 9n/10 < n$, by induction / Akra-Bazzi intuition $T(n) = \\Theta(n)$.");
            writer.println("- **Space Complexity**: $O(\\log n)$ recursion stack.");

            writer.println("\n### 4. Closest Pair of Points");
            writer.println("- **Recurrence**: $T(n) = 2T(n/2) + \\Theta(n)$");
            writer.println("- **Master Theorem**: Case 2 ($T(n) = \\Theta(n \\log n)$).");
            writer.println("- **Space Complexity**: $O(n)$ for storing sorted coordinates.");

            writer.println("\n## C. Experimental Results");
            writer.println("Detailed metrics saved in `results/results.csv`.");
            writer.println("Generated plots are placed in `docs/plots/`.\n");

            writer.println("## D. Discussion");
            writer.println("1. **Do results match theoretical complexity?**");
            writer.println("   - Yes, MergeSort, QuickSort, and Closest Pair demonstrate $O(n \\log n)$ scaling, while Deterministic Select grows linearly $O(n)$.");
            writer.println("2. **How does input structure affect performance?**");
            writer.println("   - Sorted and reverse-sorted inputs do not degrade QuickSort due to randomized pivot selection.");
            writer.println("   - Duplicate-heavy arrays slightly increase partitioning steps.");
            writer.println("3. **Why does smaller-first recursion help QuickSort?**");
            writer.println("   - Recursing on the smaller half first ensures that the stack depth never exceeds $O(\\log n)$.");
            writer.println("4. **Why does Median-of-Medians guarantee $O(n)$?**");
            writer.println("   - It guarantees a pivot that splits the elements at least 30/70, preventing the worst-case $O(n^2)$ behavior.");
            writer.println("5. **Why is Divide-and-Conquer Closest Pair faster than $O(n^2)$?**");
            writer.println("   - Instead of checking all $n(n-1)/2$ pairs, the strip check only inspects constant $O(1)$ neighbors per point.");
            writer.println("6. **Practical factors affecting performance:**");
            writer.println("   - JIT compilation warmup, cache locality, and Garbage Collection pauses during buffer allocation.\n");

            writer.println("## E. Reflection");
            writer.println("Through this assignment, we gained practical insights into implementing divide-and-conquer algorithms, measuring execution times via `System.nanoTime()`, tracking stack depth, and validating theoretical bounds using Master Theorem.");

            System.out.println("README.md generated successfully!");

        } catch (IOException e) {
            System.err.println("Error generating README.md: " + e.getMessage());
        }
    }
}