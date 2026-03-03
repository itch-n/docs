# Advanced Topics

> Master bit manipulation, intervals, and prefix sums

---

## Learning Objectives

By the end of this section you will be able to:

- Apply XOR properties to solve single-number and missing-number problems in O(1) space
- Use `n & (n-1)` and related bit tricks to count set bits, check powers of two, and reverse bits
- Merge and insert overlapping intervals in O(n log n) with a single sorted pass
- Find interval intersections and minimum meeting rooms using two-pointer and heap techniques
- Build a 1D or 2D prefix sum array and answer range-sum queries in O(1) after O(n) preprocessing
- Recognize which of the three patterns (bit manipulation, intervals, prefix sum) applies to a new problem and explain why the alternatives are weaker

---

## ELI5: Explain Like I'm 5

<div class="learner-section" markdown>

**Your task:** After implementing all patterns, explain them simply.

**Prompts to guide you:**

1. **What are these advanced techniques in one sentence each?**
    - Bit manipulation: <span class="fill-in">Bit manipulation uses ___ operations on binary digits so you can ___ without extra memory by ___</span>
    - Intervals: <span class="fill-in">Interval problems reduce to ___ after you sort by ___, because then overlaps are always ___</span>
    - Prefix sum: <span class="fill-in">A prefix sum array trades ___ space for ___ range queries by precomputing ___</span>

2. **Real-world analogies:**
    - Bit manipulation: "Like using switches that are either on or off..."
    - Intervals: "Like managing calendar appointments..."
    - Prefix sum: "Like keeping a running total..."
    - Your analogies: <span class="fill-in">[Fill in]</span>

3. **When does each pattern work?**
    - Your answers: <span class="fill-in">Bit manipulation works when ___; intervals work when ___; prefix sum works when ___</span>

</div>

---

## Quick Quiz (Do BEFORE implementing)

!!! tip "How to use this section"
    Fill in every blank **before** reading the implementation. Wrong predictions are valuable — they reveal exactly which assumptions to test. Return here after implementing to complete the "Verified" fields.

<div class="learner-section" markdown>

**Your task:** Test your intuition without looking at code. Answer these, then verify after implementation.

### Complexity Predictions

1. **XOR all elements to find single number:**
    - Time complexity: <span class="fill-in">[Your guess: O(?)]</span>
    - Space complexity: <span class="fill-in">[Your guess: O(?)]</span>
    - Verified after learning: <span class="fill-in">[Actual: O(?)]</span>

2. **Merge overlapping intervals:**
    - Time complexity: <span class="fill-in">[Your guess: O(?)]</span>
    - Why that complexity? <span class="fill-in">[Fill in your reasoning]</span>
    - Verified: <span class="fill-in">[Actual]</span>

### Scenario Predictions

**Scenario 1:** Find single number in `[2, 3, 2, 4, 3]`

- **Can you use XOR?** <span class="fill-in">[Yes/No - Why?]</span>
- **What property of XOR makes this work?** <span class="fill-in">[Fill in]</span>
- **What is 2 XOR 2?** <span class="fill-in">[Fill in]</span>
- **What is any number XOR 0?** <span class="fill-in">[Fill in]</span>

**Scenario 2:** Merge intervals `[[1,3], [2,6], [8,10], [15,18]]`

- **Must you sort first?** <span class="fill-in">[Yes/No - Why?]</span>
- **How do you check if intervals overlap?** <span class="fill-in">[Fill in the condition]</span>
- **How many intervals in final result?** <span class="fill-in">[Your prediction: ___]</span>

### Bit Manipulation Quiz

**Question:** What does `n & (n-1)` do?

- Your answer: <span class="fill-in">[Fill in before implementation]</span>
- Verified answer: <span class="fill-in">[Fill in after learning]</span>

**Question:** How to check if a number is a power of 2?

- [ ] `n % 2 == 0`
- [ ] `n > 0 && (n & (n-1)) == 0`
- [ ] `n == (n | (n-1))`
- [ ] `Math.log(n) % 2 == 0`

Verify after implementation: <span class="fill-in">[Which one(s)?]</span>

### Intervals Quiz

**Question:** For intervals `[1,4]` and `[3,6]`, do they overlap?

- Your answer: <span class="fill-in">[Yes/No]</span>
- The condition is: `start1 <= <span class="fill-in">_____</span> && start2 <= <span class="fill-in">_____</span>`
- Fill in the blanks: <span class="fill-in">[Fill in after learning]</span>

**Question:** What's faster: using a heap vs two sorted arrays for finding meeting rooms?

- Your prediction: <span class="fill-in">[Fill in]</span>
- Verified: <span class="fill-in">[Fill in after implementation]</span>

</div>

---

## Core Implementation

### Pattern 1: Bit Manipulation

**Concept:** Use bitwise operations for efficient computations.

**Use case:** XOR tricks, bit masks, counting bits, power of two.

```java
public class BitManipulation {

    /**
     * Problem: Single number (all appear twice except one)
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using XOR
     */
    public static int singleNumber(int[] nums) {
        // TODO: XOR all numbers
        // TODO: a XOR a = 0, a XOR 0 = a
        // TODO: Duplicates cancel out, single remains

        return 0; // Replace with implementation
    }

    /**
     * Problem: Number of 1 bits (Hamming weight)
     * Time: O(1), Space: O(1)
     *
     * TODO: Implement bit counting
     */
    public static int hammingWeight(int n) {
        // TODO: Count set bits
        // TODO: Method 1: Loop and check each bit
        // TODO: Method 2: n & (n-1) removes rightmost 1

        return 0; // Replace with implementation
    }

    /**
     * Problem: Reverse bits
     * Time: O(1), Space: O(1)
     *
     * TODO: Implement bit reversal
     */
    public static int reverseBits(int n) {
        // TODO: Process bit by bit
        // TODO: Extract bit: (n >> i) & 1
        // TODO: Place bit: result |= (bit << (31 - i))

        return 0; // Replace with implementation
    }

    /**
     * Problem: Missing number (0 to n with one missing)
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using XOR or math
     */
    public static int missingNumber(int[] nums) {
        // TODO: Method 1: XOR all indices and values
        // TODO: Method 2: Sum formula - sum(0..n) - sum(nums)

        return 0; // Replace with implementation
    }

    /**
     * Problem: Power of two
     * Time: O(1), Space: O(1)
     *
     * TODO: Implement power of two check
     */
    public static boolean isPowerOfTwo(int n) {
        // TODO: Power of 2 has exactly one bit set
        // TODO: Check: n > 0 && (n & (n-1)) == 0

        return false; // Replace with implementation
    }

    /**
     * Problem: Counting bits (0 to n)
     * Time: O(n), Space: O(n)
     *
     * TODO: Implement using DP with bit manipulation
     */
    public static int[] countBits(int n) {
        // TODO: dp[i] = dp[i >> 1] + (i & 1)
        // TODO: Bits in i = bits in i/2 + (1 if i is odd)

        return new int[0]; // Replace with implementation
    }

    /**
     * Problem: Sum of two integers without + operator
     * Time: O(1), Space: O(1)
     *
     * TODO: Implement using bit operations
     */
    public static int getSum(int a, int b) {
        // TODO: XOR for sum without carry
        // TODO: AND and shift for carry
        // TODO: Repeat until no carry

        return 0; // Replace with implementation
    }
}
```

**Runnable Client Code:**

```java
import java.util.*;

public class BitManipulationClient {

    public static void main(String[] args) {
        System.out.println("=== Bit Manipulation ===\n");

        // Test 1: Single number
        System.out.println("--- Test 1: Single Number ---");
        int[] arr1 = {4, 1, 2, 1, 2};
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("Single number: " + BitManipulation.singleNumber(arr1));

        // Test 2: Hamming weight
        System.out.println("\n--- Test 2: Hamming Weight ---");
        int[] numbers = {11, 128, 255};
        for (int n : numbers) {
            int weight = BitManipulation.hammingWeight(n);
            System.out.printf("%d (binary: %s): %d bits%n",
                n, Integer.toBinaryString(n), weight);
        }

        // Test 3: Reverse bits
        System.out.println("\n--- Test 3: Reverse Bits ---");
        int n = 43261596;
        System.out.printf("Original: %d (binary: %s)%n",
            n, Integer.toBinaryString(n));
        int reversed = BitManipulation.reverseBits(n);
        System.out.printf("Reversed: %d (binary: %s)%n",
            reversed, Integer.toBinaryString(reversed));

        // Test 4: Missing number
        System.out.println("\n--- Test 4: Missing Number ---");
        int[] arr2 = {3, 0, 1};
        System.out.println("Array: " + Arrays.toString(arr2));
        System.out.println("Missing: " + BitManipulation.missingNumber(arr2));

        // Test 5: Power of two
        System.out.println("\n--- Test 5: Power of Two ---");
        int[] testPowers = {1, 2, 3, 4, 16, 18};
        for (int num : testPowers) {
            boolean isPower = BitManipulation.isPowerOfTwo(num);
            System.out.printf("%d: %s%n", num, isPower ? "YES" : "NO");
        }

        // Test 6: Counting bits
        System.out.println("\n--- Test 6: Counting Bits ---");
        int num = 5;
        int[] bitCounts = BitManipulation.countBits(num);
        System.out.printf("Bit counts from 0 to %d: %s%n", num, Arrays.toString(bitCounts));

        // Test 7: Sum without + operator
        System.out.println("\n--- Test 7: Sum Without + ---");
        int a = 15, b = 27;
        int sum = BitManipulation.getSum(a, b);
        System.out.printf("%d + %d = %d%n", a, b, sum);
    }
}
```

!!! warning "Debugging Challenge — Signed Shift in Hamming Weight"

    This hammingWeight implementation has 1 critical bug:

    ```java
    public static int hammingWeight_Buggy(int n) {
        int count = 0;

        while (n > 0) {
            if ((n & 1) == 1) {
                count++;
            }
            n = n >> 1;
        }

        return count;
    }
    ```

    - Bug: <span class="fill-in">[What's the bug?]</span>
    - Test case: `n = -1` (all 32 bits are 1). Expected `32`. Actual: <span class="fill-in">[Trace through — what happens with signed right shift?]</span>

    ??? success "Answer"

        **Bug:** Using `n > 0` as the loop condition combined with `n >> 1` (signed right shift) means negative numbers loop forever — signed right shift preserves the sign bit, so a negative number never becomes zero.

        **Fix:** Use `n != 0` and unsigned right shift `>>>`:

        ```java
        while (n != 0) {
            if ((n & 1) == 1) {
                count++;
            }
            n = n >>> 1;  // Unsigned shift
        }
        ```

        **Alternative using `n & (n-1)`:**

        ```java
        while (n != 0) {
            count++;
            n = n & (n - 1);  // Remove rightmost 1 bit
        }
        ```

        This alternative avoids the shift issue entirely and is also faster because it only iterates once per set bit.

---

### Pattern 2: Intervals

**Concept:** Merge, insert, or manipulate intervals efficiently.

**Use case:** Meeting rooms, merge intervals, interval intersection.

```java
import java.util.*;

public class Intervals {

    /**
     * Problem: Merge overlapping intervals
     * Time: O(n log n), Space: O(n)
     *
     * TODO: Implement merge intervals
     */
    public static int[][] merge(int[][] intervals) {
        // TODO: Sort by start time
        // TODO: Iterate and merge overlapping intervals
        // TODO: Implement iteration/conditional logic
        // TODO: Otherwise, add previous to result

        return new int[0][0]; // Replace with implementation
    }

    /**
     * Problem: Insert interval into sorted intervals
     * Time: O(n), Space: O(n)
     *
     * TODO: Implement insert interval
     */
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        // TODO: Add all intervals before newInterval
        // TODO: Merge all overlapping intervals
        // TODO: Add all intervals after newInterval

        return new int[0][0]; // Replace with implementation
    }

    /**
     * Problem: Interval intersection
     * Time: O(m + n), Space: O(min(m,n))
     *
     * TODO: Implement interval intersection
     */
    public static int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        // TODO: Two pointers on both lists
        // TODO: Find intersection: max(start1, start2) to min(end1, end2)
        // TODO: Move pointer of interval that ends first

        return new int[0][0]; // Replace with implementation
    }

    /**
     * Problem: Minimum number of meeting rooms
     * Time: O(n log n), Space: O(n)
     *
     * TODO: Implement meeting rooms II
     */
    public static int minMeetingRooms(int[][] intervals) {
        // TODO: Method 1: Sort start and end times separately
        // TODO: Method 2: Use min-heap for end times

        return 0; // Replace with implementation
    }

    /**
     * Problem: Remove covered intervals
     * Time: O(n log n), Space: O(1)
     *
     * TODO: Implement remove covered
     */
    public static int removeCoveredIntervals(int[][] intervals) {
        // TODO: Sort by start (ascending), then end (descending)
        // TODO: Track current max end
        // TODO: Implement iteration/conditional logic

        return 0; // Replace with implementation
    }

    /**
     * Problem: Non-overlapping intervals (min removals)
     * Time: O(n log n), Space: O(1)
     *
     * TODO: Implement min removals
     */
    public static int eraseOverlapIntervals(int[][] intervals) {
        // TODO: Sort by end time (greedy)
        // TODO: Keep track of last end time
        // TODO: Implement iteration/conditional logic

        return 0; // Replace with implementation
    }
}
```

**Runnable Client Code:**

```java
import java.util.*;

public class IntervalsClient {

    public static void main(String[] args) {
        System.out.println("=== Intervals ===\n");

        // Test 1: Merge intervals
        System.out.println("--- Test 1: Merge Intervals ---");
        int[][] intervals1 = {{1,3}, {2,6}, {8,10}, {15,18}};
        System.out.println("Input: " + Arrays.deepToString(intervals1));
        int[][] merged = Intervals.merge(intervals1);
        System.out.println("Merged: " + Arrays.deepToString(merged));

        // Test 2: Insert interval
        System.out.println("\n--- Test 2: Insert Interval ---");
        int[][] intervals2 = {{1,3}, {6,9}};
        int[] newInterval = {2, 5};
        System.out.println("Intervals: " + Arrays.deepToString(intervals2));
        System.out.println("New: " + Arrays.toString(newInterval));
        int[][] inserted = Intervals.insert(intervals2, newInterval);
        System.out.println("Result: " + Arrays.deepToString(inserted));

        // Test 3: Interval intersection
        System.out.println("\n--- Test 3: Interval Intersection ---");
        int[][] first = {{0,2}, {5,10}, {13,23}, {24,25}};
        int[][] second = {{1,5}, {8,12}, {15,24}, {25,26}};
        System.out.println("First: " + Arrays.deepToString(first));
        System.out.println("Second: " + Arrays.deepToString(second));
        int[][] intersection = Intervals.intervalIntersection(first, second);
        System.out.println("Intersection: " + Arrays.deepToString(intersection));

        // Test 4: Meeting rooms
        System.out.println("\n--- Test 4: Meeting Rooms ---");
        int[][] meetings = {{0,30}, {5,10}, {15,20}};
        System.out.println("Meetings: " + Arrays.deepToString(meetings));
        int rooms = Intervals.minMeetingRooms(meetings);
        System.out.println("Min rooms needed: " + rooms);

        // Test 5: Remove covered intervals
        System.out.println("\n--- Test 5: Remove Covered Intervals ---");
        int[][] intervals3 = {{1,4}, {3,6}, {2,8}};
        System.out.println("Intervals: " + Arrays.deepToString(intervals3));
        int remaining = Intervals.removeCoveredIntervals(intervals3);
        System.out.println("Remaining after removing covered: " + remaining);

        // Test 6: Erase overlap intervals
        System.out.println("\n--- Test 6: Erase Overlap Intervals ---");
        int[][] intervals4 = {{1,2}, {2,3}, {3,4}, {1,3}};
        System.out.println("Intervals: " + Arrays.deepToString(intervals4));
        int removals = Intervals.eraseOverlapIntervals(intervals4);
        System.out.println("Min removals to make non-overlapping: " + removals);
    }
}
```

!!! warning "Debugging Challenge — Missing Math.max and Final Interval in Merge"

    This merge implementation has 2 bugs:

    ```java
    public static int[][] merge_Buggy(int[][] intervals) {
        if (intervals.length <= 1) return intervals;

        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        List<int[]> merged = new ArrayList<>();
        int[] current = intervals[0];

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= current[1]) {
                current[1] = intervals[i][1];
            } else {
                merged.add(current);
                current = intervals[i];
            }
        }

        return merged.toArray(new int[0][]);
    }
    ```

    - Bug 1: <span class="fill-in">[What's wrong with the merge line?]</span>
    - Bug 2: <span class="fill-in">[What's never added to the result?]</span>

    Test case: `[[1,4], [2,3]]`. Expected `[[1,4]]`. What do you get without the fix?
    <span class="fill-in">[Trace through]</span>

    ??? success "Answer"

        **Bug 1:** The merge line `current[1] = intervals[i][1]` should be `current[1] = Math.max(current[1], intervals[i][1])`. Without `Math.max`, a fully-contained interval like `[2,3]` inside `[1,4]` would shrink the merged interval to `[1,3]`.

        **Bug 2:** The final value of `current` is never added to `merged`. After the loop ends, the last interval (or last merged group) is still sitting in `current` but never appended. Add `merged.add(current)` before the return.

        **Correct ending:**

        ```java
        merged.add(current);  // Don't forget this!
        return merged.toArray(new int[0][]);
        ```

---

### Pattern 3: Prefix Sum

**Concept:** Precompute cumulative sums for fast range queries.

**Use case:** Subarray sum, range sum query, contiguous array.

```java
import java.util.*;

public class PrefixSum {

    /**
     * Problem: Range sum query (immutable array)
     * Time: O(1) query after O(n) preprocessing, Space: O(n)
     *
     * TODO: Implement range sum query
     */
    static class NumArray {
        private int[] prefixSum;

        public NumArray(int[] nums) {
            // TODO: Build prefix sum array
            // TODO: prefixSum[i] = sum of nums[0..i-1]
        }

        public int sumRange(int left, int right) {
            // TODO: Return prefixSum[right+1] - prefixSum[left]
            return 0; // Replace with implementation
        }
    }

    /**
     * Problem: Subarray sum equals K
     * Time: O(n), Space: O(n)
     *
     * TODO: Implement using prefix sum + hashmap
     */
    public static int subarraySum(int[] nums, int k) {
        // TODO: Use HashMap<prefixSum, frequency>
        // TODO: Implement iteration/conditional logic

        return 0; // Replace with implementation
    }

    /**
     * Problem: Contiguous array (equal 0s and 1s)
     * Time: O(n), Space: O(n)
     *
     * TODO: Implement using prefix sum
     */
    public static int findMaxLength(int[] nums) {
        // TODO: Convert 0s to -1s
        // TODO: Problem becomes: longest subarray with sum 0
        // TODO: Use HashMap<prefixSum, firstIndex>
        // TODO: Implement iteration/conditional logic

        return 0; // Replace with implementation
    }

    /**
     * Problem: Product of array except self
     * Time: O(n), Space: O(1) excluding output
     *
     * TODO: Implement using prefix/suffix products
     */
    public static int[] productExceptSelf(int[] nums) {
        // TODO: First pass: compute prefix products
        // TODO: Second pass: compute suffix products and multiply

        return new int[0]; // Replace with implementation
    }

    /**
     * Problem: Range sum query 2D (matrix)
     * Time: O(1) query after O(m*n) preprocessing, Space: O(m*n)
     *
     * TODO: Implement 2D prefix sum
     */
    static class NumMatrix {
        private int[][] prefixSum;

        public NumMatrix(int[][] matrix) {
            // TODO: Build 2D prefix sum
            // TODO: prefixSum[i][j] = sum of submatrix (0,0) to (i-1,j-1)
            // TODO: Use inclusion-exclusion principle
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            // TODO: Use inclusion-exclusion:
            return 0; // Replace with implementation
        }
    }
}
```

**Runnable Client Code:**

```java
import java.util.*;

public class PrefixSumClient {

    public static void main(String[] args) {
        System.out.println("=== Prefix Sum ===\n");

        // Test 1: Range sum query
        System.out.println("--- Test 1: Range Sum Query ---");
        int[] arr = {-2, 0, 3, -5, 2, -1};
        PrefixSum.NumArray numArray = new PrefixSum.NumArray(arr);
        System.out.println("Array: " + Arrays.toString(arr));

        int[][] queries = {{0, 2}, {2, 5}, {0, 5}};
        for (int[] query : queries) {
            int sum = numArray.sumRange(query[0], query[1]);
            System.out.printf("sumRange(%d, %d) = %d%n", query[0], query[1], sum);
        }

        // Test 2: Subarray sum equals K
        System.out.println("\n--- Test 2: Subarray Sum Equals K ---");
        int[] arr2 = {1, 1, 1};
        int k = 2;
        System.out.println("Array: " + Arrays.toString(arr2));
        System.out.println("k = " + k);
        int count = PrefixSum.subarraySum(arr2, k);
        System.out.println("Count of subarrays: " + count);

        // Test 3: Contiguous array
        System.out.println("\n--- Test 3: Contiguous Array ---");
        int[] arr3 = {0, 1, 0, 1, 1, 0};
        System.out.println("Array: " + Arrays.toString(arr3));
        int maxLen = PrefixSum.findMaxLength(arr3);
        System.out.println("Max length with equal 0s and 1s: " + maxLen);

        // Test 4: Product except self
        System.out.println("\n--- Test 4: Product Except Self ---");
        int[] arr4 = {1, 2, 3, 4};
        System.out.println("Array: " + Arrays.toString(arr4));
        int[] products = PrefixSum.productExceptSelf(arr4);
        System.out.println("Products: " + Arrays.toString(products));

        // Test 5: 2D range sum query
        System.out.println("\n--- Test 5: 2D Range Sum Query ---");
        int[][] matrix = {
            {3, 0, 1, 4, 2},
            {5, 6, 3, 2, 1},
            {1, 2, 0, 1, 5},
            {4, 1, 0, 1, 7},
            {1, 0, 3, 0, 5}
        };
        PrefixSum.NumMatrix numMatrix = new PrefixSum.NumMatrix(matrix);

        System.out.println("Matrix:");
        for (int[] row : matrix) {
            System.out.println("  " + Arrays.toString(row));
        }

        int sum = numMatrix.sumRegion(2, 1, 4, 3);
        System.out.printf("sumRegion(2, 1, 4, 3) = %d%n", sum);
    }
}
```

---

## Before/After: Why These Patterns Matter

**Your task:** Compare naive vs optimized approaches to understand the impact.

### Example 1: Find Single Number

**Problem:** Find the single number when all others appear twice.

#### Approach 1: Hash Set (Naive)

```java
// Naive approach - Track seen numbers
public static int findSingle_HashSet(int[] nums) {
    Set<Integer> seen = new HashSet<>();

    for (int num : nums) {
        if (seen.contains(num)) {
            seen.remove(num);  // Seen twice, remove
        } else {
            seen.add(num);     // First time seeing
        }
    }

    // The remaining element is the single one
    return seen.iterator().next();
}
```

**Analysis:**

- Time: O(n) - Iterate through array once
- Space: O(n) - Store up to n/2 elements in set
- For n = 10,000: ~10,000 operations + space for 5,000 integers

#### Approach 2: XOR Bit Manipulation (Optimized)

```java
// Optimized approach - Use XOR property
public static int findSingle_XOR(int[] nums) {
    int result = 0;

    for (int num : nums) {
        result ^= num;  // XOR all numbers
    }

    return result;  // Pairs cancel out, single remains
}
```

**Analysis:**

- Time: O(n) - Iterate through array once
- Space: O(1) - Only one variable
- For n = 10,000: ~10,000 operations + space for 1 integer

#### Performance Comparison

| Array Size    | HashSet Space     | XOR Space | Space Saved |
|---------------|-------------------|-----------|-------------|
| n = 1,000     | ~500 integers     | 1 integer | 99.8%       |
| n = 10,000    | ~5,000 integers   | 1 integer | 99.98%      |
| n = 1,000,000 | ~500,000 integers | 1 integer | 99.9998%    |

#### Why Does XOR Work?

**Key properties:**

- `a XOR a = 0` (any number XOR itself is 0)
- `a XOR 0 = a` (any number XOR 0 is itself)
- XOR is commutative and associative

Example with `[2, 3, 2, 4, 3]`:

```
2 XOR 3 XOR 2 XOR 4 XOR 3
= (2 XOR 2) XOR (3 XOR 3) XOR 4  // Rearrange
= 0 XOR 0 XOR 4                   // Pairs cancel
= 4                                // Answer!
```

!!! note "Key insight"
    XOR is both its own inverse and commutative, which means duplicate pairs always cancel to zero regardless of their positions in the array. This lets you collapse an O(n)-space hash table down to a single accumulator variable with no loss of information.

**After implementing, explain in your own words:**

<div class="learner-section" markdown>

- Why does XOR cancel out duplicates? <span class="fill-in">[Your answer]</span>
- When would HashSet be better than XOR? <span class="fill-in">[Your answer]</span>

</div>

---

### Example 2: Merge Intervals

**Problem:** Merge all overlapping intervals in `[[1,3], [2,6], [8,10], [15,18]]`.

#### Approach 1: Nested Loops (Naive)

```java
// Naive approach - Compare all pairs repeatedly
public static int[][] merge_BruteForce(int[][] intervals) {
    if (intervals.length <= 1) return intervals;

    List<int[]> merged = new ArrayList<>();
    boolean[] used = new boolean[intervals.length];

    for (int i = 0; i < intervals.length; i++) {
        if (used[i]) continue;

        int[] current = intervals[i].clone();
        used[i] = true;

        // Keep merging until no more overlaps found
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int j = 0; j < intervals.length; j++) {
                if (used[j]) continue;

                // Check overlap
                if (current[0] <= intervals[j][1] && intervals[j][0] <= current[1]) {
                    current[0] = Math.min(current[0], intervals[j][0]);
                    current[1] = Math.max(current[1], intervals[j][1]);
                    used[j] = true;
                    changed = true;
                }
            }
        }

        merged.add(current);
    }

    return merged.toArray(new int[0][]);
}
```

**Analysis:**

- Time: O(n²) or worse - Multiple passes needed
- Space: O(n) - Track used intervals
- For n = 1,000: Up to ~1,000,000 comparisons

#### Approach 2: Sort + Single Pass (Optimized)

```java
// Optimized approach - Sort once, merge in one pass
public static int[][] merge_Optimized(int[][] intervals) {
    if (intervals.length <= 1) return intervals;

    // Sort by start time
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

    List<int[]> merged = new ArrayList<>();
    int[] current = intervals[0];
    merged.add(current);

    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] <= current[1]) {
            // Overlap - merge by extending end
            current[1] = Math.max(current[1], intervals[i][1]);
        } else {
            // No overlap - start new interval
            current = intervals[i];
            merged.add(current);
        }
    }

    return merged.toArray(new int[0][]);
}
```

**Analysis:**

- Time: O(n log n) - Sorting dominates
- Space: O(n) - Result array
- For n = 1,000: ~10,000 comparisons (sort) + 1,000 (merge)

#### Performance Comparison

| Array Size | Brute Force (O(n²)) | Sort + Merge (O(n log n)) | Speedup |
|------------|---------------------|---------------------------|---------|
| n = 100    | ~10,000 ops         | ~700 ops                  | 14x     |
| n = 1,000  | ~1,000,000 ops      | ~10,000 ops               | 100x    |
| n = 10,000 | ~100,000,000 ops    | ~130,000 ops              | 770x    |

#### Why Does Sorting Help?

After sorting by start time, intervals are in order:

```
Unsorted: [[8,10], [1,3], [15,18], [2,6]]
Sorted:   [[1,3], [2,6], [8,10], [15,18]]
```

Now you only need to check if `current interval end >= next interval start`:

- `[1,3]` and `[2,6]`: 3 >= 2 → Merge to `[1,6]`
- `[1,6]` and `[8,10]`: 6 < 8 → Keep separate
- `[8,10]` and `[15,18]`: 10 < 15 → Keep separate

**After implementing, explain in your own words:**

<div class="learner-section" markdown>

- Why can we merge in a single pass after sorting? <span class="fill-in">[Your answer]</span>
- What intervals do we never need to compare? <span class="fill-in">[Your answer]</span>

</div>

---

!!! info "Loop back"
    Return to the Quick Quiz above and fill in all "Verified" fields. For the XOR section, can you now trace through `[2, 3, 2, 4, 3]` step-by-step? For the intervals section, can you state the overlap condition from memory without looking at the code?

---

## Case Studies

### CPU Flags and Permissions (Bit Manipulation)

Operating system kernels represent file permissions and CPU status flags as bit fields. Linux file permissions (`rwxrwxrwx`) pack nine boolean values into nine bits of a single integer. Checking whether a file is executable is a single `&` operation: `(mode & 0111) != 0`. Setting a permission bit is `mode | FLAG` and clearing it is `mode & ~FLAG`. This approach avoids allocating nine separate booleans and allows the kernel to test multiple permissions simultaneously with one masked comparison, which matters at the scale of millions of file operations per second.

### Google Calendar Scheduling (Intervals)

Calendar systems must determine meeting room availability in real time as events are added, moved, and deleted. The underlying algorithm is the same as Merge Intervals: sort events by start time and sweep through once to find conflicts or compute free slots. When a new meeting is inserted, the system only needs to check the two adjacent intervals (before and after), not all existing meetings — this is the Insert Interval pattern. Google Calendar's "Find a time" feature uses a multi-user variant that intersects each user's free-time intervals, which is the Interval Intersection pattern.

### Database Query Optimization (Prefix Sum)

Relational databases frequently need to answer aggregate queries like "total revenue between January and March." Rather than scanning every row for each query, they precompute a cumulative sum column during indexing. A range query then reduces to two array lookups and a subtraction: `prefixSum[march] - prefixSum[december]`. This is the NumArray pattern. Time-series databases like InfluxDB and column stores like ClickHouse both use variants of prefix aggregation to serve analytical queries in O(1) time regardless of the range width.

---

## Common Misconceptions

!!! warning "Misconception: signed right shift >> works the same as unsigned right shift >>> for all integers"
    In Java, `>>` is a signed right shift that fills the high bits with the sign bit. For a negative integer, every application of `>>` preserves the leading 1, so the number never reaches zero and a `while (n > 0)` loop runs forever. Always use `>>>` when iterating over bits of an integer that might be negative, or check `n != 0` as the loop condition. The `n & (n-1)` trick avoids the issue entirely by not using shifts at all.

!!! warning "Misconception: intervals must be sorted by start time for all interval problems"
    Sorting by start time is correct for merge and insert problems. But for "remove covered intervals", the right sort is ascending by start and then descending by end (so that a wide interval comes before a narrow one with the same start). For "non-overlapping intervals" (minimum removals), sorting by end time gives the greedy optimal. Applying the wrong sort order to an interval problem produces subtly wrong answers that pass most test cases but fail on inputs designed to trigger the edge case.

!!! warning "Misconception: the prefix sum array has the same length as the input array"
    The standard prefix sum array has length n+1, where `prefixSum[0] = 0` and `prefixSum[i] = sum of nums[0..i-1]`. Using length n instead forces you to special-case the query for the full range and makes the formula inconsistent. The +1 size provides a clean identity: `sumRange(left, right) = prefixSum[right+1] - prefixSum[left]` works for all valid inputs including `left = 0` without any branch.

---

## Decision Framework

**Your task:** Build decision trees for advanced patterns.

### Question 1: Which pattern to use?

**Bit manipulation when:**

- Need: <span class="fill-in">[Constant space for flags/subsets]</span>
- Operations: <span class="fill-in">[XOR, AND, OR, shifts]</span>
- Examples: <span class="fill-in">[Single number, power of 2]</span>

**Intervals when:**

- Need: <span class="fill-in">[Merge, insert, find overlaps]</span>
- Input: _[Array of [start, end] pairs]_
- Examples: <span class="fill-in">[Meeting rooms, merge intervals]</span>

**Prefix sum when:**

- Need: <span class="fill-in">[Fast range sum queries]</span>
- Trade-off: <span class="fill-in">[O(n) space for O(1) queries]</span>
- Examples: <span class="fill-in">[Subarray sum, range query]</span>

---

## Practice

### LeetCode Problems

**Easy (Complete all 4):**

- [ ] [136. Single Number](https://leetcode.com/problems/single-number/)
    - Pattern: <span class="fill-in">[XOR bit manipulation]</span>
    - Key insight: <span class="fill-in">[Fill in after solving]</span>

- [ ] [191. Number of 1 Bits](https://leetcode.com/problems/number-of-1-bits/)
    - Pattern: <span class="fill-in">[Bit counting]</span>
    - Key insight: <span class="fill-in">[Fill in after solving]</span>

- [ ] [303. Range Sum Query - Immutable](https://leetcode.com/problems/range-sum-query-immutable/)
    - Pattern: <span class="fill-in">[Prefix sum]</span>
    - Key insight: <span class="fill-in">[Fill in after solving]</span>

- [ ] [268. Missing Number](https://leetcode.com/problems/missing-number/)
    - Pattern: <span class="fill-in">[XOR or math]</span>
    - Key insight: <span class="fill-in">[Fill in after solving]</span>

**Medium (Complete 4-6):**

- [ ] [56. Merge Intervals](https://leetcode.com/problems/merge-intervals/)
    - Pattern: <span class="fill-in">[Sort + single pass]</span>
    - Difficulty: <span class="fill-in">[Rate 1-10]</span>
    - Key insight: <span class="fill-in">[Fill in]</span>

- [ ] [57. Insert Interval](https://leetcode.com/problems/insert-interval/)
    - Pattern: <span class="fill-in">[Three-phase linear scan]</span>
    - Difficulty: <span class="fill-in">[Rate 1-10]</span>
    - Key insight: <span class="fill-in">[Fill in]</span>

- [ ] [560. Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/)
    - Pattern: <span class="fill-in">[Prefix sum + hashmap]</span>
    - Difficulty: <span class="fill-in">[Rate 1-10]</span>
    - Key insight: <span class="fill-in">[Fill in]</span>

- [ ] [525. Contiguous Array](https://leetcode.com/problems/contiguous-array/)
    - Pattern: <span class="fill-in">[Prefix sum with -1 transformation]</span>
    - Difficulty: <span class="fill-in">[Rate 1-10]</span>
    - Key insight: <span class="fill-in">[Fill in]</span>

- [ ] [238. Product of Array Except Self](https://leetcode.com/problems/product-of-array-except-self/)
    - Pattern: <span class="fill-in">[Prefix/suffix products]</span>
    - Difficulty: <span class="fill-in">[Rate 1-10]</span>
    - Key insight: <span class="fill-in">[Fill in]</span>

- [ ] [435. Non-overlapping Intervals](https://leetcode.com/problems/non-overlapping-intervals/)
    - Pattern: <span class="fill-in">[Greedy, sort by end time]</span>
    - Difficulty: <span class="fill-in">[Rate 1-10]</span>
    - Key insight: <span class="fill-in">[Fill in]</span>

**Hard (Optional):**

- [ ] [352. Data Stream as Disjoint Intervals](https://leetcode.com/problems/data-stream-as-disjoint-intervals/)
    - Pattern: <span class="fill-in">[Dynamic interval merge]</span>
    - Key insight: <span class="fill-in">[Fill in after solving]</span>

- [ ] [307. Range Sum Query - Mutable](https://leetcode.com/problems/range-sum-query-mutable/)
    - Pattern: <span class="fill-in">[Segment tree or BIT]</span>
    - Key insight: <span class="fill-in">[Fill in after solving]</span>

---

## Test Your Understanding

Answer these after completing the implementations and practice problems. Return here and fill in your answers without looking at notes.

1. **Walk through `[4, 1, 2, 1, 2]` step by step using XOR to find the single number. Show each intermediate value of the accumulator.**
   <span class="fill-in">[Your answer]</span>

2. **Why does the `isPowerOfTwo` check require `n > 0 && (n & (n-1)) == 0` rather than just `(n & (n-1)) == 0`? Give two specific inputs where the shorter version returns the wrong answer.**
   <span class="fill-in">[Your answer]</span>

3. **You are given unsorted intervals `[[3,5],[1,4],[6,8]]`. Trace through the sort-and-merge algorithm step by step and show the state of the result list after each iteration.**
   <span class="fill-in">[Your answer]</span>

4. **The prefix sum approach to subarraySum initializes the map with `{0: 1}` before iterating. Why is this initialization necessary? Give a concrete example where omitting it produces a wrong answer.**
   <span class="fill-in">[Your answer]</span>

5. **A 2D prefix sum matrix has dimensions `(m+1) x (n+1)` for an `m x n` input matrix. Explain what `prefixSum[0][j]` and `prefixSum[i][0]` represent, and why these base cases are needed for the sumRegion formula to work correctly.**
   <span class="fill-in">[Your answer]</span>
