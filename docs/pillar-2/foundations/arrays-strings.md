# Arrays & Strings

## Learning Objectives
- Master two-pointer and sliding window techniques
- Implement efficient string manipulation algorithms
- Understand time/space complexity trade-offs

## Core Patterns

### Two Pointer Technique
```java
public int[] twoSumSorted(int[] nums, int target) {
    // Find two numbers that sum to target in sorted array
    int left = 0, right = nums.length - 1;
    
    while (left < right) {
        int currentSum = nums[left] + nums[right];
        if (currentSum == target) {
            return new int[]{left, right};
        } else if (currentSum < target) {
            left++;
        } else {
            right--;
        }
    }
    
    return new int[]{}; // Empty array
}

public boolean isPalindrome(String s) {
    // Check if string is palindrome (ignoring case/spaces)
    int left = 0, right = s.length() - 1;
    
    while (left < right) {
        // Skip non-alphanumeric characters
        while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
            left++;
        }
        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
            right--;
        }
        
        if (Character.toLowerCase(s.charAt(left)) !=
            Character.toLowerCase(s.charAt(right))) {
            return false;
        }
        
        left++;
        right--;
    }
    
    return true;
}
```

### Sliding Window
```java
public int longestSubstringKDistinct(String s, int k) {
    // Find longest substring with at most k distinct characters
    if (k == 0) return 0;
    
    Map<Character, Integer> charCount = new HashMap<>();
    int left = 0, maxLength = 0;
    
    for (int right = 0; right < s.length(); right++) {
        // Expand window
        char rightChar = s.charAt(right);
        charCount.put(rightChar, charCount.getOrDefault(rightChar, 0) + 1);
        
        // Contract window if needed
        while (charCount.size() > k) {
            char leftChar = s.charAt(left);
            charCount.put(leftChar, charCount.get(leftChar) - 1);
            if (charCount.get(leftChar) == 0) {
                charCount.remove(leftChar);
            }
            left++;
        }
        
        maxLength = Math.max(maxLength, right - left + 1);
    }
    
    return maxLength;
}

public String minWindowSubstring(String s, String t) {
    // Find minimum window substring containing all chars in t
    if (s == null || t == null || s.length() < t.length()) {
        return "";
    }
    
    // Count characters in t
    Map<Character, Integer> tCount = new HashMap<>();
    for (char c : t.toCharArray()) {
        tCount.put(c, tCount.getOrDefault(c, 0) + 1);
    }
    
    int required = tCount.size();
    int formed = 0;
    Map<Character, Integer> windowCounts = new HashMap<>();
    
    int left = 0;
    int minLen = Integer.MAX_VALUE;
    int minLeft = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char rightChar = s.charAt(right);
        windowCounts.put(rightChar, windowCounts.getOrDefault(rightChar, 0) + 1);
        
        if (tCount.containsKey(rightChar) &&
            windowCounts.get(rightChar).equals(tCount.get(rightChar))) {
            formed++;
        }
        
        // Contract window
        while (formed == required && left <= right) {
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                minLeft = left;
            }
            
            char leftChar = s.charAt(left);
            windowCounts.put(leftChar, windowCounts.get(leftChar) - 1);
            if (tCount.containsKey(leftChar) &&
                windowCounts.get(leftChar) < tCount.get(leftChar)) {
                formed--;
            }
            
            left++;
        }
    }
    
    return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
}
```

## String Manipulation

### KMP Algorithm (Pattern Matching)
```java
public int[] buildLPS(String pattern) {
    // Build longest prefix suffix array for KMP
    int[] lps = new int[pattern.length()];
    int length = 0;
    int i = 1;
    
    while (i < pattern.length()) {
        if (pattern.charAt(i) == pattern.charAt(length)) {
            length++;
            lps[i] = length;
            i++;
        } else {
            if (length != 0) {
                length = lps[length - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }
    }
    
    return lps;
}

public List<Integer> kmpSearch(String text, String pattern) {
    // Find all occurrences of pattern in text
    List<Integer> matches = new ArrayList<>();
    if (pattern.isEmpty()) {
        return matches;
    }
    
    int[] lps = buildLPS(pattern);
    int i = 0, j = 0;
    
    while (i < text.length()) {
        if (text.charAt(i) == pattern.charAt(j)) {
            i++;
            j++;
        }
        
        if (j == pattern.length()) {
            matches.add(i - j);
            j = lps[j - 1];
        } else if (i < text.length() && text.charAt(i) != pattern.charAt(j)) {
            if (j != 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
    }
    
    return matches;
}
```

## Advanced Concepts

### Rolling Hash (Rabin-Karp)
- **Use Case**: Fast substring search with multiple patterns
- **Time Complexity**: O(n + m) average, O(nm) worst case
- **Applications**: Plagiarism detection, DNA sequence matching

### Trie (Prefix Tree)
- **Use Case**: Auto-complete, spell checkers, IP routing
- **Space Trade-off**: Memory usage vs lookup speed
- **Compressed Tries**: Radix trees for space optimization

## Complexity Analysis

| Algorithm | Time | Space | Best For |
|-----------|------|-------|----------|
| **Two Pointers** | O(n) | O(1) | Sorted arrays, palindromes |
| **Sliding Window** | O(n) | O(k) | Subarray/substring problems |
| **KMP** | O(n + m) | O(m) | Pattern matching |
| **Rolling Hash** | O(n) avg | O(1) | Multiple pattern search |

## Practice Problems
- **Easy**: Valid Palindrome, Two Sum, Remove Duplicates
- **Medium**: Longest Substring Without Repeating, Group Anagrams
- **Hard**: Minimum Window Substring, Text Justification