# Arrays & Strings

## Learning Objectives
- Master two-pointer and sliding window techniques
- Implement efficient string manipulation algorithms
- Understand time/space complexity trade-offs

## Core Patterns

### Two Pointer Technique
```python
def two_sum_sorted(nums, target):
    """Find two numbers that sum to target in sorted array."""
    left, right = 0, len(nums) - 1
    
    while left < right:
        current_sum = nums[left] + nums[right]
        if current_sum == target:
            return [left, right]
        elif current_sum < target:
            left += 1
        else:
            right -= 1
    
    return []

def is_palindrome(s):
    """Check if string is palindrome (ignoring case/spaces)."""
    left, right = 0, len(s) - 1
    
    while left < right:
        # Skip non-alphanumeric characters
        while left < right and not s[left].isalnum():
            left += 1
        while left < right and not s[right].isalnum():
            right -= 1
            
        if s[left].lower() != s[right].lower():
            return False
            
        left += 1
        right -= 1
    
    return True
```

### Sliding Window
```python
def longest_substring_k_distinct(s, k):
    """Find longest substring with at most k distinct characters."""
    if k == 0:
        return 0
    
    char_count = {}
    left = 0
    max_length = 0
    
    for right in range(len(s)):
        # Expand window
        char_count[s[right]] = char_count.get(s[right], 0) + 1
        
        # Contract window if needed
        while len(char_count) > k:
            char_count[s[left]] -= 1
            if char_count[s[left]] == 0:
                del char_count[s[left]]
            left += 1
        
        max_length = max(max_length, right - left + 1)
    
    return max_length

def min_window_substring(s, t):
    """Find minimum window substring containing all chars in t."""
    if not s or not t or len(s) < len(t):
        return ""
    
    # Count characters in t
    t_count = {}
    for char in t:
        t_count[char] = t_count.get(char, 0) + 1
    
    required = len(t_count)
    formed = 0
    window_counts = {}
    
    left = 0
    min_len = float('inf')
    min_left = 0
    
    for right in range(len(s)):
        char = s[right]
        window_counts[char] = window_counts.get(char, 0) + 1
        
        if char in t_count and window_counts[char] == t_count[char]:
            formed += 1
        
        # Contract window
        while formed == required and left <= right:
            if right - left + 1 < min_len:
                min_len = right - left + 1
                min_left = left
            
            char = s[left]
            window_counts[char] -= 1
            if char in t_count and window_counts[char] < t_count[char]:
                formed -= 1
            
            left += 1
    
    return "" if min_len == float('inf') else s[min_left:min_left + min_len]
```

## String Manipulation

### KMP Algorithm (Pattern Matching)
```python
def build_lps(pattern):
    """Build longest prefix suffix array for KMP."""
    lps = [0] * len(pattern)
    length = 0
    i = 1
    
    while i < len(pattern):
        if pattern[i] == pattern[length]:
            length += 1
            lps[i] = length
            i += 1
        else:
            if length != 0:
                length = lps[length - 1]
            else:
                lps[i] = 0
                i += 1
    
    return lps

def kmp_search(text, pattern):
    """Find all occurrences of pattern in text."""
    if not pattern:
        return []
    
    lps = build_lps(pattern)
    matches = []
    i = j = 0
    
    while i < len(text):
        if text[i] == pattern[j]:
            i += 1
            j += 1
        
        if j == len(pattern):
            matches.append(i - j)
            j = lps[j - 1]
        elif i < len(text) and text[i] != pattern[j]:
            if j != 0:
                j = lps[j - 1]
            else:
                i += 1
    
    return matches
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