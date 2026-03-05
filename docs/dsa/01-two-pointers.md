# Two Pointers

> Reduce O(n²) to O(n) by using two indices moving through data

---

## Learning Objectives

By the end of this topic you will be able to:

- Explain why two pointers reduces O(n²) nested-loop problems to O(n)
- Implement all three two-pointer variants: opposite-direction, same-direction, and different-speed
- Identify which variant applies to a given problem (pair search vs. in-place modification vs. linked-list properties)
- Analyse the time and space complexity of each pattern
- Recognise when the sorted-array requirement is necessary and when it is not
- Compare two-pointer solutions against HashSet alternatives and justify the choice

---

## ELI5: Explain Like I'm 5

<div class="learner-section" markdown>

**Your task:** After implementing all three patterns, explain them simply.

**Prompts to guide you:**

1. **What is the two pointers pattern in one sentence?**
    - Your answer: <span class="fill-in">[Two pointers is a technique where instead of checking every pair with nested loops, you place one index at each end (or use two indexes moving together) so that ___ and each step eliminates multiple candidates at once]</span>

2. **Why is it faster than nested loops?**
    - Your answer: <span class="fill-in">[Instead of revisiting the same pairs over and over, each pointer move rules out ___ candidates permanently, so the total work is ___ instead of ___]</span>

3. **Real-world analogy:**
    - Example: "Two pointers is like two people searching..."
    - Your analogy: <span class="fill-in">[Fill in]</span>

4. **When does this pattern work?**
    - Your answer: <span class="fill-in">[Fill in after solving problems]</span>

5. **When does this pattern fail?**
    - Your answer: <span class="fill-in">[Fill in after trying unsorted arrays]</span>

</div>

---

## Quick Quiz (Do BEFORE implementing)

!!! tip "How to use this section"
    Write your best guess in each fill-in span **before** reading any implementation code. After you finish coding and running the tests, come back and fill in the "Verified" answers. The gap between your prediction and the actual answer is where the real learning happens.

<div class="learner-section" markdown>

**Your task:** Test your intuition without looking at code. Answer these, then verify after implementation.

### Complexity Predictions

1. **Two nested loops searching for a pair:**
    - Time complexity: <span class="fill-in">[Your guess: O(?)]</span>
    - Verified after learning: <span class="fill-in">[Actual: O(?)]</span>

2. **Two pointers searching for a pair in sorted array:**
    - Time complexity: <span class="fill-in">[Your guess: O(?)]</span>
    - Space complexity: <span class="fill-in">[Your guess: O(?)]</span>
    - Verified: <span class="fill-in">[Actual]</span>

3. **Speedup calculation:**
    - If n = 1,000, nested loops = n² = <span class="fill-in">_____</span> operations
    - Two pointers = n = <span class="fill-in">_____</span> operations
    - Speedup factor: <span class="fill-in">_____</span> times faster

### Scenario Predictions

**Scenario 1:** Find pair that sums to 10 in `[1, 3, 5, 7, 9]`

- **Can you use two pointers?** <span class="fill-in">[Yes/No - Why?]</span>
- **Starting positions:** left = <span class="fill-in">___</span>, right = <span class="fill-in">___</span>
- **If sum = 8 (too small), which pointer moves?** <span class="fill-in">[Left/Right - Why?]</span>
- **If sum = 12 (too big), which pointer moves?** <span class="fill-in">[Left/Right - Why?]</span>

**Scenario 2:** Find pair that sums to 10 in `[9, 3, 1, 7, 5]` (unsorted)

- **Can you use two pointers directly?** <span class="fill-in">[Yes/No - Why?]</span>
- **What must you do first?** <span class="fill-in">[Fill in]</span>

**Scenario 3:** Remove duplicates from `[1, 1, 2, 2, 3]`

- **Which pattern applies?** <span class="fill-in">[Opposite/Same/Different speed]</span>
- **Why that pattern?** <span class="fill-in">[Fill in your reasoning]</span>

### Trade-off Quiz

**Question:** When would HashSet be BETTER than two pointers for finding pairs?

- Your answer: <span class="fill-in">[Fill in before implementation]</span>
- Verified answer: <span class="fill-in">[Fill in after learning]</span>

**Question:** What's the MAIN requirement for opposite-direction two pointers?

- [ ] Array must be sorted
- [ ] Array must have even length
- [ ] Array must contain unique elements
- [ ] Array must be positive integers

Verify after implementation: <span class="fill-in">[Which one(s)?]</span>


</div>

---

## Core Implementation

### Pattern 1: Opposite Direction Pointers

**Concept:** Start from both ends, move toward each other.

**Use case:** Palindromes, pair sum in sorted array.

```java
public class OppositeDirectionPointers {

    /**
     * Problem: Check if string is a palindrome
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using two pointers from opposite ends
     */
    public static boolean isPalindrome(String s) {
        // TODO: Use two pointers moving towards each other
        // Consider the loop termination condition

        return false; // Replace with implementation
    }

    /**
     * Problem: Find pair in sorted array that sums to target
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement two-pointer pair sum
     */
    public static int[] twoSum(int[] nums, int target) {
        // TODO: Start pointers at opposite ends
        // How should pointers move based on current sum vs target?

        return new int[] {-1, -1}; // Replace with implementation
    }

    /**
     * Problem: Reverse array in-place
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using two pointers
     */
    public static void reverseArray(int[] arr) {
        // TODO: Use two pointers to swap elements
        // What positions should they start at?
    }
}
```

**Runnable Client Code:**

```java
public class OppositeDirectionClient {

    public static void main(String[] args) {
        System.out.println("=== Opposite Direction Two Pointers ===\n");

        // Test 1: Palindrome check
        System.out.println("--- Test 1: Palindrome ---");
        String[] testStrings = {"racecar", "hello", "noon", "a", ""};

        for (String s : testStrings) {
            boolean result = OppositeDirectionPointers.isPalindrome(s);
            System.out.printf("isPalindrome(\"%s\") = %b%n", s, result);
        }

        // Test 2: Two sum in sorted array
        System.out.println("\n--- Test 2: Two Sum ---");
        int[] sortedArray = {1, 3, 5, 7, 9, 11};
        int target = 12;

        int[] result = OppositeDirectionPointers.twoSum(sortedArray, target);
        System.out.printf("Array: %s%n", Arrays.toString(sortedArray));
        System.out.printf("Target: %d%n", target);
        System.out.printf("Pair indices: %s%n", Arrays.toString(result));
        if (result[0] != -1) {
            System.out.printf("Values: %d + %d = %d%n",
                sortedArray[result[0]], sortedArray[result[1]], target);
        }

        // Test 3: Reverse array
        System.out.println("\n--- Test 3: Reverse Array ---");
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println("Before: " + Arrays.toString(arr));
        OppositeDirectionPointers.reverseArray(arr);
        System.out.println("After:  " + Arrays.toString(arr));
    }
}
```

!!! warning "Debugging Challenge — Broken Palindrome Checker"
    The `isPalindrome_Buggy` below has **2 bugs**. Find them before running the code.

    ```java
    public static boolean isPalindrome_Buggy(String s) {
        int left = 0;
        int right = s.length();
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    ```

    - Bug 1: <span class="fill-in">[What's the bug?]</span>
    - Bug 2: <span class="fill-in">[What's the bug?]</span>

    ??? success "Answer"
        **Bug 1 (initialisation):** `right` should be `s.length() - 1`, not `s.length()`. Array indices are 0-based, so `s.charAt(s.length())` throws `StringIndexOutOfBoundsException`.

        **Bug 2 (trick question):** Once Bug 1 is fixed the code is correct — the apparent second bug was a deliberate distraction. The lesson: always fix the most fundamental error first and re-trace before hunting for more.

---

### Pattern 2: Same Direction Pointers (Slow/Fast)

**Concept:** Both pointers move left to right, at different speeds.

**Use case:** Remove duplicates, partition array, in-place modifications.

```java
public class SameDirectionPointers {

    /**
     * Problem: Remove duplicates from sorted array in-place
     * Return new length
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using slow/fast pointers
     */
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;

        // TODO: One pointer tracks unique elements, other explores
        // When should you copy a value? What should be returned?

        return 0; // Replace with implementation
    }

    /**
     * Problem: Move all zeros to end, maintain order of non-zeros
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using slow/fast pointers
     */
    public static void moveZeroes(int[] nums) {
        // TODO: Track position for non-zero elements
        // How can swapping help maintain order?
    }

    /**
     * Problem: Partition array - all elements < pivot go left
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement partition logic
     */
    public static int partition(int[] arr, int pivot) {
        // TODO: Maintain a boundary between partitions
        // What determines when to swap elements?

        return 0; // Replace with implementation
    }
}
```

**Runnable Client Code:**

```java
public class SameDirectionClient {

    public static void main(String[] args) {
        System.out.println("=== Same Direction Two Pointers ===\n");

        // Test 1: Remove duplicates
        System.out.println("--- Test 1: Remove Duplicates ---");
        int[] arr1 = {1, 1, 2, 2, 2, 3, 4, 4, 5};
        System.out.println("Before: " + Arrays.toString(arr1));

        int newLength = SameDirectionPointers.removeDuplicates(arr1);
        System.out.println("After:  " + Arrays.toString(Arrays.copyOf(arr1, newLength)));
        System.out.println("New length: " + newLength);

        // Test 2: Move zeros
        System.out.println("\n--- Test 2: Move Zeros ---");
        int[] arr2 = {0, 1, 0, 3, 12, 0, 5};
        System.out.println("Before: " + Arrays.toString(arr2));
        SameDirectionPointers.moveZeroes(arr2);
        System.out.println("After:  " + Arrays.toString(arr2));

        // Test 3: Partition
        System.out.println("\n--- Test 3: Partition ---");
        int[] arr3 = {7, 2, 9, 1, 5, 3, 8};
        int pivot = 5;
        System.out.println("Before: " + Arrays.toString(arr3));
        System.out.println("Pivot:  " + pivot);

        int partitionIdx = SameDirectionPointers.partition(arr3, pivot);
        System.out.println("After:  " + Arrays.toString(arr3));
        System.out.println("Partition index: " + partitionIdx);
        System.out.println("(All elements before index " + partitionIdx + " are < " + pivot + ")");
    }
}
```

!!! warning "Debugging Challenge — Broken Remove Duplicates"
    The `removeDuplicates_Buggy` below has **1 critical bug** and **1 return-value bug**. Trace through `[1, 1, 2, 2, 3]` manually before checking the answer.

    ```java
    public static int removeDuplicates_Buggy(int[] nums) {
        int slow = 0;
        int fast = 1;
        while (fast < nums.length) {
            if (nums[fast] != nums[slow]) {
                nums[slow] = nums[fast];            slow++;
            }
            fast++;
        }
        return slow;
    }
    ```

    - Bug 1: <span class="fill-in">[What gets overwritten incorrectly?]</span>
    - Bug 2: <span class="fill-in">[What should the return value be?]</span>

    ??? success "Answer"
        **Bug 1:** `slow++` must come **before** `nums[slow] = nums[fast]`. The current order overwrites the unique element at `slow` before the write pointer has advanced, corrupting the first unique value.

        **Correct:**
        ```java
        if (nums[fast] != nums[slow]) {
            slow++;
            nums[slow] = nums[fast];
        }
        ```

        **Bug 2:** Should return `slow + 1`, not `slow`. The length is one more than the last written index.

---

### Pattern 3: Different Speed Pointers

**Concept:** One pointer moves faster than the other.

**Use case:** Linked list cycle detection, finding middle element.

```java
public class DifferentSpeedPointers {

    // Simple ListNode definition
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * Problem: Detect cycle in linked list
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using slow/fast pointers
     */
    public static boolean hasCycle(ListNode head) {
        // TODO: Move pointers at different speeds
        // What happens when they meet?

        return false; // Replace with implementation
    }

    /**
     * Problem: Find middle of linked list
     * If even length, return second middle node
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using slow/fast pointers
     */
    public static ListNode findMiddle(ListNode head) {
        // TODO: Use different pointer speeds
        // Where is the slow pointer when fast reaches the end?

        return null; // Replace with implementation
    }

    /**
     * Problem: Find kth node from end
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using two pointers with gap
     */
    public static ListNode findKthFromEnd(ListNode head, int k) {
        // TODO: Create a fixed gap between pointers
        // When one reaches the end, where is the other?

        return null; // Replace with implementation
    }

    // Helper: Create linked list from array
    static ListNode createList(int[] values) {
        if (values.length == 0) return null;

        ListNode head = new ListNode(values[0]);
        ListNode current = head;

        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }

        return head;
    }

    // Helper: Print linked list
    static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) System.out.print(" -> ");
            current = current.next;
        }
        System.out.println();
    }
}
```

**Runnable Client Code:**

```java
public class DifferentSpeedClient {

    public static void main(String[] args) {
        System.out.println("=== Different Speed Two Pointers ===\n");

        // Test 1: Cycle detection
        System.out.println("--- Test 1: Cycle Detection ---");

        // List without cycle: 1 -> 2 -> 3 -> 4 -> 5
        ListNode list1 = DifferentSpeedPointers.createList(new int[]{1, 2, 3, 4, 5});
        System.out.print("List: ");
        DifferentSpeedPointers.printList(list1);
        System.out.println("Has cycle: " + DifferentSpeedPointers.hasCycle(list1));

        // List with cycle: 1 -> 2 -> 3 -> 4 -> 5 -> (back to 3)
        ListNode list2 = DifferentSpeedPointers.createList(new int[]{1, 2, 3, 4, 5});
        ListNode node3 = list2.next.next; // Node with value 3
        ListNode tail = list2.next.next.next.next; // Node with value 5
        tail.next = node3; // Create cycle

        System.out.println("\nList with cycle (5 -> 3):");
        System.out.println("Has cycle: " + DifferentSpeedPointers.hasCycle(list2));

        // Test 2: Find middle
        System.out.println("\n--- Test 2: Find Middle ---");
        ListNode list3 = DifferentSpeedPointers.createList(new int[]{1, 2, 3, 4, 5});
        System.out.print("List (odd length): ");
        DifferentSpeedPointers.printList(list3);

        ListNode middle = DifferentSpeedPointers.findMiddle(list3);
        System.out.println("Middle value: " + middle.val);

        ListNode list4 = DifferentSpeedPointers.createList(new int[]{1, 2, 3, 4, 5, 6});
        System.out.print("List (even length): ");
        DifferentSpeedPointers.printList(list4);

        middle = DifferentSpeedPointers.findMiddle(list4);
        System.out.println("Middle value: " + middle.val);

        // Test 3: Kth from end
        System.out.println("\n--- Test 3: Kth From End ---");
        ListNode list5 = DifferentSpeedPointers.createList(new int[]{1, 2, 3, 4, 5});
        System.out.print("List: ");
        DifferentSpeedPointers.printList(list5);

        for (int k = 1; k <= 3; k++) {
            ListNode kthNode = DifferentSpeedPointers.findKthFromEnd(list5, k);
            System.out.printf("%dth from end: %d%n", k, kthNode.val);
        }
    }
}
```

---

!!! info "Loop back"
    Before moving on, return to the ELI5 section and Quick Quiz at the top. Fill in any answers you left blank. If your complexity predictions were off, write a one-sentence explanation of why — that note is more valuable than the correct answer alone.

---

## Before/After: Why This Pattern Matters

**Your task:** Compare naive vs optimized approaches to understand the impact.

### Example: Find Pair Sum

**Problem:** Find two numbers in a sorted array that sum to a target.

#### Approach 1: Brute Force (Nested Loops)

```java
// Naive approach - Check all possible pairs
public static boolean hasPairSum_BruteForce(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] + nums[j] == target) {
                return true;
            }
        }
    }
    return false;
}
```

**Analysis:**

- Time: O(n²) - For each element, check all remaining elements
- Space: O(1) - No extra space
- For n = 10,000: ~100,000,000 operations

#### Approach 2: Two Pointers (Optimized)

```java
// Optimized approach - Use two pointers from opposite ends
public static boolean hasPairSum_TwoPointers(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;

    while (left < right) {
        int sum = nums[left] + nums[right];
        if (sum == target) return true;
        if (sum < target) left++;    // Need larger sum
        else right--;                 // Need smaller sum
    }

    return false;
}
```

**Analysis:**

- Time: O(n) - Each pointer moves at most n/2 steps
- Space: O(1) - No extra space
- For n = 10,000: ~10,000 operations

#### Performance Comparison

| Array Size | Brute Force (O(n²)) | Two Pointers (O(n)) | Speedup |
|------------|---------------------|---------------------|---------|
| n = 100    | 10,000 ops          | 100 ops             | 100x    |
| n = 1,000  | 1,000,000 ops       | 1,000 ops           | 1,000x  |
| n = 10,000 | 100,000,000 ops     | 10,000 ops          | 10,000x |

**Your calculation:** For n = 5,000, the speedup is approximately _____ times faster.

#### Why Does Two Pointers Work?

**Key insight to understand:**

In a sorted array `[1, 3, 5, 7, 9]` looking for sum = 10:

```
Step 1: left=0 (val=1), right=4 (val=9), sum=10 → FOUND!
```

If we were looking for sum = 12:

```
Step 1: left=0 (val=1), right=4 (val=9), sum=10 (too small)
        → Move left++ because we need a LARGER sum

Step 2: left=1 (val=3), right=4 (val=9), sum=12 → FOUND!
```

!!! note "Why can we skip pairs safely?"
    When `sum < target`, moving `right--` makes the sum even smaller — that direction is provably useless. Likewise, when `sum > target`, moving `left++` only increases the sum further. Sorted order guarantees that each pointer move eliminates an entire row or column of pairs from consideration, which is the source of the O(n) behaviour.

**After implementing, explain in your own words:**

<div class="learner-section" markdown>

- Why does sorted order matter? <span class="fill-in">[Your answer]</span>
- What pairs do we skip and why is it safe? <span class="fill-in">[Your answer]</span>

</div>

---

## Common Misconceptions

!!! warning "Misconception 1: Two pointers always requires a sorted array"
    Opposite-direction pointers for pair-sum **do** require sorted order, but same-direction and different-speed patterns do not. `moveZeroes`, `removeDuplicates`, and Floyd's cycle detection all work on unsorted or linked-list input. The sorting requirement is specific to the convergence proof for opposite-direction, not to the technique itself.

!!! warning "Misconception 2: Two pointers and sliding window are the same thing"
    Both use two indices, but the goals differ. Two pointers typically search for a **pair of elements** satisfying a condition, while sliding window maintains a **contiguous subarray** and tracks aggregate state (sum, frequency map) within it. A dynamic sliding window is a two-pointer technique, but two pointers is a broader category.

!!! warning "Misconception 3: Same-direction slow/fast pointers need different starting positions"
    It is tempting to start `slow = 0, fast = 1` always, but the correct starting position depends on the problem. For `removeDuplicates` both start at 0 (or 0 and 1); for Floyd's cycle detection both start at `head`. Internalise the **invariant** each pattern maintains rather than memorising the starting positions.

---

## Decision Framework

<div class="learner-section" markdown>

**Your task:** Build decision trees for when to use two pointers.

### Question 1: Is the data sorted?

Answer after solving problems:

- **Why does sorting matter?** <span class="fill-in">[Fill in]</span>
- **Can two pointers work on unsorted arrays?** <span class="fill-in">[Yes/No - when?]</span>
- **Your observation:** <span class="fill-in">[Fill in based on testing]</span>

### Question 2: What are you looking for?

Answer for each pattern:

**Opposite direction when:**

- Looking for: <span class="fill-in">[Pairs? Palindromes? What else?]</span>
- Movement rule: <span class="fill-in">[How do pointers move?]</span>
- Example problems: <span class="fill-in">[List problems you solved]</span>

**Same direction when:**

- Looking for: <span class="fill-in">[Duplicates? Partitioning? What else?]</span>
- Movement rule: <span class="fill-in">[How do slow/fast pointers move?]</span>
- Example problems: <span class="fill-in">[List problems you solved]</span>

**Different speed when:**

- Looking for: <span class="fill-in">[Cycles? Middle? What else?]</span>
- Movement rule: <span class="fill-in">[How do pointers move at different speeds?]</span>
- Example problems: <span class="fill-in">[List problems you solved]</span>

### Your Decision Tree

Build this after solving practice problems:
```mermaid
flowchart LR
    Start["Two Pointers Pattern Selection"]

    Q1{"Is data sorted?"}
    Start --> Q1
    N2["Continue"]
    Q1 -->|"YES"| N2
    N3["Can you sort? Or use same-direction for partitioning"]
    Q1 -->|"NO"| N3
    Q4{"What are you searching for?"}
    Start --> Q4
    Q5{"Pair with property<br/>(sum, product)?"}
    Q6{"Modify array in-place?"}
    Q7{"Linked list property<br/>(cycle, middle, kth)?"}
    Q8{"Check palindrome?"}
```

</div>

---

## Practice

<div class="learner-section" markdown>

### LeetCode Problems

**Easy (Complete all 3):**

- [ ] [125. Valid Palindrome](https://leetcode.com/problems/valid-palindrome/)
    - Pattern: <span class="fill-in">[Which one?]</span>
    - Your solution time: <span class="fill-in">___</span>
    - Key insight: <span class="fill-in">[Fill in after solving]</span>

- [ ] [26. Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)
    - Pattern: <span class="fill-in">[Which one?]</span>
    - Your solution time: <span class="fill-in">___</span>
    - Key insight: <span class="fill-in">[Fill in]</span>

- [ ] [283. Move Zeroes](https://leetcode.com/problems/move-zeroes/)
    - Pattern: <span class="fill-in">[Which one?]</span>
    - Your solution time: <span class="fill-in">___</span>
    - Key insight: <span class="fill-in">[Fill in]</span>

**Medium (Complete 2-3):**

- [ ] [15. 3Sum](https://leetcode.com/problems/3sum/)
    - Pattern: <span class="fill-in">[Extension of which pattern?]</span>
    - Difficulty: <span class="fill-in">[Rate 1-10]</span>
    - Key insight: <span class="fill-in">[Fill in]</span>
    - Mistake made: <span class="fill-in">[Fill in if any]</span>

- [ ] [11. Container With Most Water](https://leetcode.com/problems/container-with-most-water/)
    - Pattern: <span class="fill-in">[Which one?]</span>
    - Difficulty: <span class="fill-in">[Rate 1-10]</span>
    - Key insight: <span class="fill-in">[Fill in]</span>

- [ ] [167. Two Sum II](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/)
    - Pattern: <span class="fill-in">[Which one?]</span>
    - Comparison to Two Sum I: <span class="fill-in">[How is it different?]</span>

**Hard (Optional):**

- [ ] [42. Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/)
    - Pattern: <span class="fill-in">[Which variant?]</span>
    - Key insight: <span class="fill-in">[Fill in after solving]</span>

</div>

---

## Test Your Understanding

Answer these questions without looking at your notes. Write a sentence or two for each.

1. **You are given an unsorted array and asked to find whether any two elements sum to a target. A colleague suggests two pointers. Is that correct? What must be done first, and what is the total time complexity including that step?**

2. **Explain in your own words why Floyd's cycle detection (slow/fast pointers) is guaranteed to detect a cycle if one exists. What happens to the gap between the pointers inside a cycle?**

3. **You implement `removeDuplicates` and get the wrong length back. You print the array and the values look correct. What is the most likely bug, and why does the array look right even though the length is wrong?**

4. **A friend says "same-direction pointers are just a slower version of sliding window." What is the key distinction? Give one problem that clearly belongs to same-direction two pointers but not sliding window.**

5. **Trapping Rain Water (LeetCode 42) can be solved with O(n) space using prefix arrays, or with O(1) space using two pointers. Describe the core insight that lets the two-pointer solution avoid the prefix arrays.**

