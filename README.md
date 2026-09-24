# Assignment 1: Divide-and-Conquer Algorithm Analysis

## A. Project Overview
This project implements four classic Divide-and-Conquer algorithms in Java:
1. **MergeSort**: Linear merge, reusable auxiliary buffer, cutoff to Insertion Sort.
2. **QuickSort**: Randomized pivot selection, in-place partitioning, tail-recursion optimization.
3. **Deterministic Select**: Median-of-Medians pivot selection, guaranteed linear time.
4. **Closest Pair of Points**: Divide-and-conquer geometry algorithm on 2D plane.

## B. Algorithm Analysis
### 1. MergeSort
- **Recurrence**: $T(n) = 2T(n/2) + \Theta(n)$
- **Master Theorem**: Case 2 ($a = 2, b = 2, f(n) = n \implies \Theta(n \log n)$).
- **Space Complexity**: $\Theta(n)$ due to auxiliary buffer.

### 2. QuickSort
- **Recurrence**: $T(n) = T(k) + T(n - k - 1) + \Theta(n)$
- **Best/Average Case**: $\Theta(n \log n)$
- **Worst Case**: $O(n^2)$ (mitigated using randomized pivot).
- **Recursion Depth**: Bound to $O(\log n)$ by recursing on smaller sub-array first.

### 3. Deterministic Select (Median-of-Medians)
- **Recurrence**: $T(n) \le T(n/5) + T(7n/10) + \Theta(n)$
- **Analysis**: Since $n/5 + 7n/10 = 9n/10 < n$, by induction / Akra-Bazzi intuition $T(n) = \Theta(n)$.
- **Space Complexity**: $O(\log n)$ recursion stack.

### 4. Closest Pair of Points
- **Recurrence**: $T(n) = 2T(n/2) + \Theta(n)$
- **Master Theorem**: Case 2 ($T(n) = \Theta(n \log n)$).
- **Space Complexity**: $O(n)$ for storing sorted coordinates.

## C. Experimental Results
Detailed metrics saved in `results/results.csv`.
Generated plots are placed in `docs/plots/`.

## D. Discussion
1. **Do results match theoretical complexity?**
   - Yes, MergeSort, QuickSort, and Closest Pair demonstrate $O(n \log n)$ scaling, while Deterministic Select grows linearly $O(n)$.
2. **How does input structure affect performance?**
   - Sorted and reverse-sorted inputs do not degrade QuickSort due to randomized pivot selection.
   - Duplicate-heavy arrays slightly increase partitioning steps.
3. **Why does smaller-first recursion help QuickSort?**
   - Recursing on the smaller half first ensures that the stack depth never exceeds $O(\log n)$.
4. **Why does Median-of-Medians guarantee $O(n)$?**
   - It guarantees a pivot that splits the elements at least 30/70, preventing the worst-case $O(n^2)$ behavior.
5. **Why is Divide-and-Conquer Closest Pair faster than $O(n^2)$?**
   - Instead of checking all $n(n-1)/2$ pairs, the strip check only inspects constant $O(1)$ neighbors per point.
6. **Practical factors affecting performance:**
   - JIT compilation warmup, cache locality, and Garbage Collection pauses during buffer allocation.

## E. Reflection
Through this assignment, we gained practical insights into implementing divide-and-conquer algorithms, measuring execution times via `System.nanoTime()`, tracking stack depth, and validating theoretical bounds using Master Theorem.
