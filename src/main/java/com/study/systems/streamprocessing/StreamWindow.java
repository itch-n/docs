package com.study.systems.streamprocessing;

import java.util.*;

/**
 * Stream Windowing: Group events into time-based windows
 *
 * Window Types:
 * - Tumbling: Fixed-size, non-overlapping (e.g., every 5 minutes)
 * - Sliding: Fixed-size, overlapping (e.g., last 5 minutes, updated every 1 minute)
 * - Session: Dynamic size based on inactivity gaps
 */
public class StreamWindow<K, V> {

    static class Event<K, V> {
        K key;
        V value;
        long timestamp; // Event time

        Event(K key, V value, long timestamp) {
            this.key = key;
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    static class WindowResult<K> {
        K key;
        long windowStart;
        long windowEnd;
        long count;

        WindowResult(K key, long windowStart, long windowEnd, long count) {
            this.key = key;
            this.windowStart = windowStart;
            this.windowEnd = windowEnd;
            this.count = count;
        }

        @Override
        public String toString() {
            return String.format("Window[%d-%d] key=%s count=%d",
                windowStart, windowEnd, key, count);
        }
    }

    /**
     * Tumbling Window: Fixed, non-overlapping time buckets
     * Time: O(1) per event, Space: O(W*K) where W=windows, K=keys
     *
     * TODO: Implement tumbling window
     * 1. Determine which window the event belongs to
     * 2. windowStart = (timestamp / windowSize) * windowSize
     * 3. Aggregate events in the same window
     * 4. Emit results when window closes
     */
    public static <K, V> Map<Long, Map<K, Long>> tumblingWindow(
            List<Event<K, V>> events,
            long windowSizeMs) {

        Map<Long, Map<K, Long>> windows = new TreeMap<>();

        // TODO: Process each event
        //
        //     // Get or create window
        //     Map<K, Long> window = windows.computeIfAbsent(windowStart, k -> new HashMap<>());
        //
        //     // Aggregate (count in this case)
        //     window.merge(event.key, 1L, Long::sum);
        //   }

        return windows; // Replace
    }

    /**
     * Sliding Window: Overlapping windows
     * Time: O(N) per event where N=num overlapping windows, Space: O(W*K)
     *
     * TODO: Implement sliding window
     * 1. For each event, determine ALL windows it belongs to
     * 2. An event at time T belongs to windows:
     *    [T-windowSize+slide, T-windowSize+2*slide, ..., T]
     * 3. Update all overlapping windows
     */
    public static <K, V> Map<Long, Map<K, Long>> slidingWindow(
            List<Event<K, V>> events,
            long windowSizeMs,
            long slideMs) {

        Map<Long, Map<K, Long>> windows = new TreeMap<>();

        // TODO: Process each event
        //
        //     // Add event to all overlapping windows
        //     for (long windowStart = firstWindowStart;
        //          windowStart <= lastWindowStart;
        //          windowStart += slideMs) {
        //       Map<K, Long> window = windows.computeIfAbsent(windowStart, k -> new HashMap<>());
        //       window.merge(event.key, 1L, Long::sum);
        //     }
        //   }

        return windows; // Replace
    }

    /**
     * Session Window: Group events with inactivity gap
     * Time: O(log N) per event, Space: O(S*K) where S=sessions
     *
     * TODO: Implement session window
     * 1. Sort events by key and timestamp
     * 2. For each key, group events within gap threshold
     * 3. Start new session if gap > threshold
     * 4. Merge sessions if events arrive late
     */
    public static <K, V> List<WindowResult<K>> sessionWindow(
            List<Event<K, V>> events,
            long gapMs) {

        List<WindowResult<K>> results = new ArrayList<>();

        // TODO: Group events by key

        // TODO: Implement iteration/conditional logic
        //
        //     // Sort by timestamp
        //     keyEvents.sort(Comparator.comparingLong(e -> e.timestamp));
        //
        //     // Create sessions
        //     long sessionStart = keyEvents.get(0).timestamp;
        //     long lastTimestamp = sessionStart;
        //     long count = 0;
        //
        //     for (Event<K, V> event : keyEvents) {
        //       if (event.timestamp - lastTimestamp > gapMs) {
        //         // Close current session
        //         results.add(new WindowResult<>(key, sessionStart, lastTimestamp, count));
        //
        //         // Start new session
        //         sessionStart = event.timestamp;
        //         count = 0;
        //       }
        //
        //       lastTimestamp = event.timestamp;
        //       count++;
        //     }
        //
        //     // Close final session
        //     results.add(new WindowResult<>(key, sessionStart, lastTimestamp, count));
        //   }

        return results; // Replace
    }

    /**
     * Helper: Print window results
     */
    public static <K> void printWindows(Map<Long, Map<K, Long>> windows) {
        for (Map.Entry<Long, Map<K, Long>> entry : windows.entrySet()) {
            long windowStart = entry.getKey();
            System.out.println("Window [" + windowStart + "]:");
            for (Map.Entry<K, Long> keyCount : entry.getValue().entrySet()) {
                System.out.println("  " + keyCount.getKey() + ": " + keyCount.getValue());
            }
        }
    }


    // --- demo (moved from StreamWindowClient) ---

public static void main(String[] args) {
        System.out.println("=== Stream Windowing ===\n");

        // Create sample events (userId, action, timestamp)
        List<Event<String, String>> events = Arrays.asList(
            new Event<>("user1", "click", 1000L),
            new Event<>("user2", "click", 2000L),
            new Event<>("user1", "click", 3000L),
            new Event<>("user1", "click", 6000L),
            new Event<>("user2", "click", 7000L),
            new Event<>("user1", "click", 11000L),
            new Event<>("user2", "click", 12000L),
            new Event<>("user1", "click", 15000L),
            new Event<>("user2", "click", 16000L)
        );

        // Test 1: Tumbling Window (5 second windows)
        System.out.println("--- Test 1: Tumbling Window (5s) ---");
        Map<Long, Map<String, Long>> tumbling =
            tumblingWindow(events, 5000L);
        printWindows(tumbling);

        // Test 2: Sliding Window (5s window, 2s slide)
        System.out.println("\n--- Test 2: Sliding Window (5s window, 2s slide) ---");
        Map<Long, Map<String, Long>> sliding =
            slidingWindow(events, 5000L, 2000L);
        printWindows(sliding);

        // Test 3: Session Window (3s gap)
        System.out.println("\n--- Test 3: Session Window (3s gap) ---");
        List<WindowResult<String>> sessions =
            sessionWindow(events, 3000L);
        for (WindowResult<String> result : sessions) {
            System.out.println(result);
        }

        // Test 4: Different gap threshold
        System.out.println("\n--- Test 4: Session Window (5s gap) ---");
        List<WindowResult<String>> sessions2 =
            sessionWindow(events, 5000L);
        for (WindowResult<String> result : sessions2) {
            System.out.println(result);
        }
    }
}
