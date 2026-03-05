# Stream Processing

> Real-time data processing with windowing, watermarks, and stateful operations

---

## Learning Objectives

By the end of this section you should be able to:

- Implement tumbling, sliding, and session windows and explain when each is appropriate
- Describe how watermarks track event-time progress and why they enable late-data handling
- Distinguish event time from processing time and explain the implications for out-of-order streams
- Implement stateful stream processing with TTL-bounded state and explain why unbounded state is dangerous
- Choose between at-most-once, at-least-once, and exactly-once semantics for a given use case
- Debug common stream processing bugs including window boundary errors, watermark misconfiguration, and state memory leaks

---

## ELI5: Explain Like I'm 5

<div class="learner-section" markdown>

**Your task:** After implementing stream processing patterns, explain them simply.

**Prompts to guide you:**

1. **What is stream processing in one sentence?**
    - Your answer: <span class="fill-in">Stream processing is a ___ that handles events ___ as they arrive, instead of ___</span>

2. **What is a window in stream processing?**
    - Your answer: <span class="fill-in">A window is a ___ that groups events by ___ so you can ___</span>

3. **Real-world analogy for tumbling window:**
    - Example: "A tumbling window is like counting cars that pass every 5 minutes..."
    - Your analogy: <span class="fill-in">[Fill in]</span>

4. **What are watermarks in one sentence?**
    - Your answer: <span class="fill-in">A watermark is a ___ that tells the system "all events before time ___ have ___"</span>

5. **What is the difference between event time and processing time?**
    - Your answer: <span class="fill-in">Event time is when ___, while processing time is when ___; they differ because ___</span>

6. **Real-world analogy for late data handling:**
    - Example: "Late data is like receiving a postcard that was sent last week..."
    - Your analogy: <span class="fill-in">[Fill in]</span>

</div>

---

## Quick Quiz (Do BEFORE implementing)

!!! tip "How to use this section"
    Fill in your best guesses **before** reading any code. After implementing each pattern, return here and check your predictions. The goal is to build intuition, not to get everything right on the first pass.

<div class="learner-section" markdown>

**Your task:** Test your intuition about stream processing. Answer these, then verify after implementation.

### Complexity Predictions

1. **Tumbling window processing:**
    - Time complexity per event: <span class="fill-in">[Your guess: O(?)]</span>
    - Space complexity for K keys over W windows: <span class="fill-in">[Your guess: O(?)]</span>
    - Verified after learning: <span class="fill-in">[Actual]</span>

2. **Sliding window vs tumbling window:**
    - If sliding window size = 10s, slide = 2s, how many windows per event? <span class="fill-in">[Guess]</span>
    - Space overhead compared to tumbling: <span class="fill-in">[Guess: X times larger]</span>
    - Verified: <span class="fill-in">[Actual]</span>

3. **State size calculation:**
    - If you have 100K unique keys, each storing 1KB of state
    - Total memory needed: <span class="fill-in">[Calculate]</span>
    - After 1 hour with TTL = 5 minutes: <span class="fill-in">[Will it grow unbounded?]</span>

### Scenario Predictions

**Scenario 1:** Events arriving: timestamps [100, 200, 150, 300] (out of order)

- **Tumbling window (size=100ms):** Which windows do they belong to?
    - Event@100ms → Window <span class="fill-in">[0-100? 100-200?]</span>
    - Event@200ms → Window <span class="fill-in">[Fill in]</span>
    - Event@150ms → Window <span class="fill-in">[Fill in]</span>
    - Event@300ms → Window <span class="fill-in">[Fill in]</span>

- **If watermark = 250ms and allowed lateness = 50ms:**
    - Event@150ms arrives when watermark=250ms: <span class="fill-in">[Accept or Drop?]</span>
    - Event@100ms arrives when watermark=250ms: <span class="fill-in">[Accept or Drop?]</span>
    - Why? <span class="fill-in">[Fill in your reasoning]</span>

**Scenario 2:** Session window with 3-second gap

Events for user1: [1000ms, 2000ms, 3000ms, 7000ms, 8000ms]

- **How many sessions?** <span class="fill-in">[Guess]</span>
- **Session boundaries:** <span class="fill-in">[Fill in]</span>
- **If event@4500ms arrives late, what happens?** <span class="fill-in">[New session or merge?]</span>

**Scenario 3:** Processing 100K events/second

- **Without state:** Memory usage <span class="fill-in">[Constant? Growing?]</span>
- **With state (no TTL):** Memory usage <span class="fill-in">[Constant? Growing?]</span>
- **With state (TTL=5min):** Memory usage <span class="fill-in">[Constant? Growing?]</span>
- **Your reasoning:** <span class="fill-in">[Fill in]</span>

### Watermark Quiz

**Question:** Watermark = 1000ms, allowed lateness = 200ms

For a tumbling window [0-1000ms]:

- When does the window start computing? <span class="fill-in">[Fill in]</span>
- When does the window close and stop accepting data? <span class="fill-in">[Fill in]</span>
- Event@900ms arrives at processing time 1500ms: <span class="fill-in">[Accepted?]</span>
- Event@900ms arrives at processing time 1300ms: <span class="fill-in">[Accepted?]</span>

**Question:** What happens if you set allowed lateness = 0?

- Your answer: <span class="fill-in">[Fill in before implementation]</span>
- Verified answer: <span class="fill-in">[Fill in after learning]</span>

### Trade-off Quiz

**Question:** When would batch processing be BETTER than stream processing?

- Your answer: <span class="fill-in">[Fill in]</span>
- Verified: <span class="fill-in">[Fill in after implementation]</span>

**Question:** What's the MAIN trade-off of exactly-once processing?

- [ ] Uses more CPU
- [ ] Requires more memory
- [ ] Increases latency
- [ ] All of the above

Verify after implementation: <span class="fill-in">[Which one(s)?]</span>

**Question:** Event time vs Processing time

Event occurs at 10:00:00 but arrives at system at 10:00:05:

- Event time = <span class="fill-in">[Fill in]</span>
- Processing time = <span class="fill-in">[Fill in]</span>
- Which one should windowing use? <span class="fill-in">[Why?]</span>

</div>

---

## Case Studies: Stream Processing in the Wild

### Netflix: Real-time Streaming Analytics with Flink

- **Pattern:** Windowed Aggregations on event streams.
- **How it works:** Netflix's infrastructure generates a massive stream of events: every "play," "pause," "buffer,"
  and "finish" action from millions of viewers is published to Apache Kafka. They use Apache Flink to process this
  stream in real-time. For example, a Flink job might use a **10-second tumbling window** to count the number of
  playback errors per region, allowing engineers to spot and react to regional outages instantly.
- **Key Takeaway:** Stream processing is essential for real-time operational monitoring at scale. By using windowed
  aggregations, raw event streams can be transformed into meaningful, actionable metrics for dashboards and alerting
  systems.

### LinkedIn Feed Updates: Real-time Content Delivery

- **Pattern:** Stream-Table Joins.
- **How it works:** LinkedIn's feed is a combination of real-time activity and user profile data. When a user you follow
  shares an article, that's a real-time event on a stream. To render the feed, their stream processing system (Apache
  Samza) must join this event stream with a table stream containing user profile data (like the user's name and
  headline). The result is a fully enriched feed item, delivered in near real-time.
- **Key Takeaway:** Stream processing isn't just about counting events. It's often about enriching real-time events with
  static or slow-moving data from tables to provide context and create a complete picture for the end-user.

### Cloudflare: DDoS Detection with Sliding Windows

- **Pattern:** Sliding Window analysis for anomaly detection.
- **How it works:** Cloudflare protects websites from DDoS attacks by analyzing vast streams of network request data. A
  stream processor might use a **1-minute sliding window**, evaluated every 5 seconds, to track the request count per IP
  address. If the count for any IP suddenly spikes and crosses a predefined threshold within that window, the system
  automatically identifies it as a potential attack and can block the IP at the edge.
- **Key Takeaway:** Sliding windows are perfect for detecting anomalies in real-time data. By continuously analyzing
  recent activity, systems can identify and react to security threats or performance issues much faster than traditional
  batch-based analysis would allow.

---

## Core Implementation

### Pattern 1: Windowing (Tumbling, Sliding, Session)

**Concept:** Group streaming data into finite chunks for aggregation.

**Use case:** Real-time analytics, metrics aggregation, event counting.

```java
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
}
```

!!! warning "Debugging Challenge — Window Boundary Off-by-One"
    The following `tumblingWindow_Buggy` method has **2 bugs**. It is supposed to count events per key per window.

    ```java
    public static Map<Long, Map<String, Long>> tumblingWindow_Buggy(
            List<Event<String, String>> events,
            long windowSize) {

        Map<Long, Map<String, Long>> windows = new TreeMap<>();

        for (Event<String, String> event : events) {
            long windowStart = event.timestamp / windowSize;

            Map<String, Long> window = windows.computeIfAbsent(windowStart, k -> new HashMap<>());

            window.put(event.key, 1L);
        }

        return windows;
    }
    ```

    - Bug 1: What is wrong with the `windowStart` calculation?
    - Bug 2: What is wrong with how each event is counted?

    ??? success "Answer"
        **Bug 1:** `windowStart` should be `(event.timestamp / windowSize) * windowSize` to align to proper window boundaries. Without the multiply, keys are window indices, not actual timestamps — so `windowStart = 1500/1000 = 1` instead of the correct `1000`.

        **Bug 2:** `window.put(event.key, 1L)` always **overwrites** the count with 1. It should use `window.merge(event.key, 1L, Long::sum)` to accumulate counts instead.

**Runnable Client Code:**

```java
import java.util.*;

public class StreamWindowClient {

    public static void main(String[] args) {
        System.out.println("=== Stream Windowing ===\n");

        // Create sample events (userId, action, timestamp)
        List<StreamWindow.Event<String, String>> events = Arrays.asList(
            new StreamWindow.Event<>("user1", "click", 1000L),
            new StreamWindow.Event<>("user2", "click", 2000L),
            new StreamWindow.Event<>("user1", "click", 3000L),
            new StreamWindow.Event<>("user1", "click", 6000L),
            new StreamWindow.Event<>("user2", "click", 7000L),
            new StreamWindow.Event<>("user1", "click", 11000L),
            new StreamWindow.Event<>("user2", "click", 12000L),
            new StreamWindow.Event<>("user1", "click", 15000L),
            new StreamWindow.Event<>("user2", "click", 16000L)
        );

        // Test 1: Tumbling Window (5 second windows)
        System.out.println("--- Test 1: Tumbling Window (5s) ---");
        Map<Long, Map<String, Long>> tumbling =
            StreamWindow.tumblingWindow(events, 5000L);
        StreamWindow.printWindows(tumbling);

        // Test 2: Sliding Window (5s window, 2s slide)
        System.out.println("\n--- Test 2: Sliding Window (5s window, 2s slide) ---");
        Map<Long, Map<String, Long>> sliding =
            StreamWindow.slidingWindow(events, 5000L, 2000L);
        StreamWindow.printWindows(sliding);

        // Test 3: Session Window (3s gap)
        System.out.println("\n--- Test 3: Session Window (3s gap) ---");
        List<StreamWindow.WindowResult<String>> sessions =
            StreamWindow.sessionWindow(events, 3000L);
        for (StreamWindow.WindowResult<String> result : sessions) {
            System.out.println(result);
        }

        // Test 4: Different gap threshold
        System.out.println("\n--- Test 4: Session Window (5s gap) ---");
        List<StreamWindow.WindowResult<String>> sessions2 =
            StreamWindow.sessionWindow(events, 5000L);
        for (StreamWindow.WindowResult<String> result : sessions2) {
            System.out.println(result);
        }
    }
}
```

---

### Pattern 2: Watermarks and Late Data

**Concept:** Handle out-of-order events and determine when to close windows.

**Use case:** Distributed systems, network delays, mobile data sync.

```java
import java.util.*;

/**
 * Watermarks: Track event time progress in the stream
 *
 * Watermark Properties:
 * - Monotonically increasing timestamp
 * - Indicates "all events before this time have been seen"
 * - Allows system to close windows and emit results
 * - Late data: events arriving after watermark
 */
public class WatermarkProcessor<K, V> {

    static class Event<K, V> {
        K key;
        V value;
        long eventTime;      // When event actually occurred
        long processingTime; // When event was processed

        Event(K key, V value, long eventTime, long processingTime) {
            this.key = key;
            this.value = value;
            this.eventTime = eventTime;
            this.processingTime = processingTime;
        }
    }

    static class WindowState<K> {
        K key;
        long windowStart;
        long windowEnd;
        long count;
        boolean closed;

        WindowState(K key, long windowStart, long windowEnd) {
            this.key = key;
            this.windowStart = windowStart;
            this.windowEnd = windowEnd;
            this.count = 0;
            this.closed = false;
        }
    }

    private final long windowSize;
    private final long allowedLateness;
    private long currentWatermark;

    // Active windows: windowStart -> key -> state
    private Map<Long, Map<K, WindowState<K>>> windows;

    // Late data count
    private long lateEventCount;

    public WatermarkProcessor(long windowSize, long allowedLateness) {
        this.windowSize = windowSize;
        this.allowedLateness = allowedLateness;
        this.currentWatermark = 0;
        this.windows = new TreeMap<>();
        this.lateEventCount = 0;
    }

    /**
     * Process event with watermark tracking
     * Time: O(log W) where W=windows, Space: O(W*K)
     *
     * TODO: Implement event processing with watermarks
     * 1. Update watermark based on event time
     * 2. Assign event to window
     * 3. Check if event is late (eventTime < watermark - allowedLateness)
     * 4. Close windows when watermark passes windowEnd + allowedLateness
     */
    public void processEvent(Event<K, V> event) {
        // TODO: Update watermark (typically: eventTime - maxDelay)

        // TODO: Calculate window for this event

        // TODO: Check if event is too late

        // TODO: Get or create window state

        // TODO: Update state if window not closed

        // TODO: Close windows that are ready
    }

    /**
     * Close windows that have passed watermark + allowedLateness
     * Time: O(W*K), Space: O(1)
     *
     * TODO: Implement window closing logic
     */
    private void closeCompletedWindows() {
        List<Long> toRemove = new ArrayList<>();

        // TODO: Check each window
        //
        //     // Close if watermark passed windowEnd + allowedLateness
        //     if (currentWatermark >= windowEnd + allowedLateness) {
        //       Map<K, WindowState<K>> window = entry.getValue();
        //
        //       // Emit results for each key in window
        //       for (WindowState<K> state : window.values()) {
        //         if (!state.closed) {
        //           emitResult(state);
        //           state.closed = true;
        //         }
        //       }
        //
        //       toRemove.add(windowStart);
        //     }
        //   }

        // TODO: Remove closed windows
    }

    /**
     * Emit window result (in production: send to output stream)
     */
    private void emitResult(WindowState<K> state) {
        System.out.printf("EMIT: Window[%d-%d] key=%s count=%d watermark=%d%n",
            state.windowStart, state.windowEnd, state.key, state.count, currentWatermark);
    }

    /**
     * Generate periodic watermark (for idle streams)
     * Time: O(1), Space: O(1)
     *
     * TODO: Implement periodic watermark generation
     */
    public void generatePeriodicWatermark(long timestamp) {
        // TODO: Advance watermark
    }

    /**
     * Get current watermark
     */
    public long getWatermark() {
        return currentWatermark;
    }

    /**
     * Get late event count
     */
    public long getLateEventCount() {
        return lateEventCount;
    }

    /**
     * Get active window count
     */
    public int getActiveWindowCount() {
        return windows.size();
    }
}
```

**Runnable Client Code:**

```java
import java.util.*;

public class WatermarkClient {

    public static void main(String[] args) {
        System.out.println("=== Watermarks and Late Data ===\n");

        WatermarkProcessor<String, String> processor =
            new WatermarkProcessor<>(5000L, 2000L); // 5s window, 2s late

        // Test 1: In-order events
        System.out.println("--- Test 1: In-Order Events ---");
        List<WatermarkProcessor.Event<String, String>> events1 = Arrays.asList(
            new WatermarkProcessor.Event<>("user1", "click", 1000L, 1000L),
            new WatermarkProcessor.Event<>("user1", "click", 2000L, 2000L),
            new WatermarkProcessor.Event<>("user2", "click", 3000L, 3000L)
        );

        for (WatermarkProcessor.Event<String, String> event : events1) {
            processor.processEvent(event);
        }
        System.out.println("Watermark: " + processor.getWatermark());

        // Test 2: Advance watermark to close window
        System.out.println("\n--- Test 2: Close Window ---");
        processor.generatePeriodicWatermark(8000L);
        System.out.println("Active windows: " + processor.getActiveWindowCount());

        // Test 3: Out-of-order events (within allowed lateness)
        System.out.println("\n--- Test 3: Out-of-Order (Within Lateness) ---");
        WatermarkProcessor.Event<String, String> lateEvent =
            new WatermarkProcessor.Event<>("user2", "click", 4000L, 9000L);
        processor.processEvent(lateEvent);

        // Test 4: Very late event (outside allowed lateness)
        System.out.println("\n--- Test 4: Very Late Event (Dropped) ---");
        WatermarkProcessor.Event<String, String> veryLateEvent =
            new WatermarkProcessor.Event<>("user1", "click", 500L, 10000L);
        processor.processEvent(veryLateEvent);
        System.out.println("Late event count: " + processor.getLateEventCount());

        // Test 5: Multiple windows with different keys
        System.out.println("\n--- Test 5: Multiple Windows ---");
        WatermarkProcessor<String, String> processor2 =
            new WatermarkProcessor<>(5000L, 1000L);

        List<WatermarkProcessor.Event<String, String>> events2 = Arrays.asList(
            new WatermarkProcessor.Event<>("A", "x", 1000L, 1000L),
            new WatermarkProcessor.Event<>("B", "y", 2000L, 2000L),
            new WatermarkProcessor.Event<>("A", "x", 3000L, 3000L),
            new WatermarkProcessor.Event<>("A", "x", 7000L, 7000L),
            new WatermarkProcessor.Event<>("B", "y", 8000L, 8000L)
        );

        for (WatermarkProcessor.Event<String, String> event : events2) {
            processor2.processEvent(event);
        }

        // Close all windows
        processor2.generatePeriodicWatermark(15000L);
    }
}
```

---

### Pattern 3: Stateful Stream Processing

**Concept:** Maintain state across events for aggregations, joins, and enrichment.

**Use case:** Running totals, user sessions, stream joins, enrichment.

```java
import java.util.*;

/**
 * Stateful Stream Processing
 *
 * State Types:
 * - Value State: Single value per key
 * - List State: List of values per key
 * - Map State: Nested key-value map per key
 *
 * State Backends:
 * - In-memory (fast, not fault-tolerant)
 * - RocksDB (persistent, fault-tolerant)
 */
public class StatefulProcessor<K, V> {

    static class Event<K, V> {
        K key;
        V value;
        long timestamp;

        Event(K key, V value, long timestamp) {
            this.key = key;
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    static class StateDescriptor<S> {
        String name;
        Class<S> stateType;
        long ttlMs; // Time-to-live for state cleanup

        StateDescriptor(String name, Class<S> stateType, long ttlMs) {
            this.name = name;
            this.stateType = stateType;
            this.ttlMs = ttlMs;
        }
    }

    /**
     * Value State: Single value per key
     * Time: O(1), Space: O(K)
     */
    static class ValueState<K, S> {
        private Map<K, S> state;
        private Map<K, Long> lastAccess; // For TTL
        private long ttlMs;

        ValueState(long ttlMs) {
            this.state = new HashMap<>();
            this.lastAccess = new HashMap<>();
            this.ttlMs = ttlMs;
        }

        /**
         * Get state for key
         *
         * TODO: Implement get with TTL check
         * 1. Check if key exists
         * 2. Check if state expired (current time - lastAccess > ttl)
         * 3. If expired, remove and return null
         * 4. Otherwise return value
         */
        public S get(K key, long currentTime) {
            // TODO: Check expiration
            return null; // Replace
        }

        /**
         * Update state for key
         *
         * TODO: Implement update with TTL tracking
         */
        public void update(K key, S value, long currentTime) {
            // TODO: Update state and last access time
        }

        /**
         * Clear state for key
         */
        public void clear(K key) {
            state.remove(key);
            lastAccess.remove(key);
        }
    }

    /**
     * List State: Append-only list per key
     * Time: O(1) append, O(N) iterate, Space: O(K*N)
     */
    static class ListState<K, S> {
        private Map<K, List<S>> state;
        private Map<K, Long> lastAccess;
        private long ttlMs;

        ListState(long ttlMs) {
            this.state = new HashMap<>();
            this.lastAccess = new HashMap<>();
            this.ttlMs = ttlMs;
        }

        /**
         * Append value to list
         *
         * TODO: Implement append
         */
        public void append(K key, S value, long currentTime) {
            // TODO: Get or create list and append
        }

        /**
         * Get all values for key
         *
         * TODO: Implement get with TTL check
         */
        public List<S> get(K key, long currentTime) {
            // TODO: Check expiration similar to ValueState
            return new ArrayList<>(); // Replace
        }
    }

    /**
     * Example: Running sum aggregation
     * Time: O(1) per event, Space: O(K)
     *
     * TODO: Implement stateful aggregation
     */
    public static class RunningSumProcessor {
        private ValueState<String, Long> sumState;

        public RunningSumProcessor(long ttlMs) {
            this.sumState = new ValueState<>(ttlMs);
        }

        /**
         * Process event and update running sum
         *
         * TODO: Implement running sum
         * 1. Get current sum for key
         * 2. Add new value
         * 3. Update state
         * 4. Return new sum
         */
        public long process(Event<String, Long> event) {
            // TODO: Get current sum

            // TODO: Add new value

            // TODO: Update state

            // TODO: Return result

            return 0L; // Replace
        }

        public Long getCurrentSum(String key, long timestamp) {
            return sumState.get(key, timestamp);
        }
    }

    /**
     * Example: Stream-Stream Join
     * Time: O(1) per event, Space: O(K*W) where W=window size
     *
     * TODO: Implement stream join
     */
    public static class StreamJoinProcessor {
        private ListState<String, Event<String, String>> leftState;
        private ListState<String, Event<String, String>> rightState;
        private long joinWindowMs;

        public StreamJoinProcessor(long joinWindowMs, long stateTtl) {
            this.leftState = new ListState<>(stateTtl);
            this.rightState = new ListState<>(stateTtl);
            this.joinWindowMs = joinWindowMs;
        }

        /**
         * Process left stream event
         *
         * TODO: Implement left stream processing
         * 1. Store event in left state
         * 2. Look for matching events in right state within join window
         * 3. Emit joined results
         */
        public List<String> processLeft(Event<String, String> event) {
            List<String> results = new ArrayList<>();

            // TODO: Store in left state

            // TODO: Find matches in right state

            return results; // Replace
        }

        /**
         * Process right stream event
         *
         * TODO: Implement right stream processing (symmetric to left)
         */
        public List<String> processRight(Event<String, String> event) {
            List<String> results = new ArrayList<>();

            // TODO: Similar to processLeft but reversed

            return results; // Replace
        }
    }

    /**
     * State cleanup: Remove expired state
     * Time: O(K), Space: O(1)
     *
     * TODO: Implement periodic state cleanup
     */
    public static void cleanupExpiredState(ValueState<?, ?> state, long currentTime) {
        // TODO: Iterate through all keys and remove expired entries
        // In production: RocksDB handles this with compaction
    }
}
```

**Runnable Client Code:**

```java
import java.util.*;

public class StatefulProcessorClient {

    public static void main(String[] args) {
        System.out.println("=== Stateful Stream Processing ===\n");

        // Test 1: Running sum
        System.out.println("--- Test 1: Running Sum ---");
        StatefulProcessor.RunningSumProcessor sumProcessor =
            new StatefulProcessor.RunningSumProcessor(10000L);

        List<StatefulProcessor.Event<String, Long>> sumEvents = Arrays.asList(
            new StatefulProcessor.Event<>("user1", 10L, 1000L),
            new StatefulProcessor.Event<>("user1", 20L, 2000L),
            new StatefulProcessor.Event<>("user2", 5L, 2500L),
            new StatefulProcessor.Event<>("user1", 15L, 3000L),
            new StatefulProcessor.Event<>("user2", 10L, 3500L)
        );

        for (StatefulProcessor.Event<String, Long> event : sumEvents) {
            long sum = sumProcessor.process(event);
            System.out.printf("Key=%s Value=%d RunningSum=%d%n",
                event.key, event.value, sum);
        }

        // Test 2: Stream join
        System.out.println("\n--- Test 2: Stream Join ---");
        StatefulProcessor.StreamJoinProcessor joinProcessor =
            new StatefulProcessor.StreamJoinProcessor(2000L, 10000L);

        // Left stream events
        System.out.println("Processing left stream:");
        StatefulProcessor.Event<String, String> left1 =
            new StatefulProcessor.Event<>("order1", "LeftA", 1000L);
        List<String> joined1 = joinProcessor.processLeft(left1);
        System.out.println("  " + left1.key + ": " + joined1);

        // Right stream events
        System.out.println("Processing right stream:");
        StatefulProcessor.Event<String, String> right1 =
            new StatefulProcessor.Event<>("order1", "RightX", 1500L);
        List<String> joined2 = joinProcessor.processRight(right1);
        System.out.println("  " + right1.key + ": " + joined2);

        // More events
        StatefulProcessor.Event<String, String> left2 =
            new StatefulProcessor.Event<>("order2", "LeftB", 2000L);
        List<String> joined3 = joinProcessor.processLeft(left2);
        System.out.println("  " + left2.key + ": " + joined3);

        // Test 3: State TTL
        System.out.println("\n--- Test 3: State TTL ---");
        StatefulProcessor.ValueState<String, String> ttlState =
            new StatefulProcessor.ValueState<>(2000L); // 2s TTL

        ttlState.update("key1", "value1", 1000L);
        System.out.println("Stored at t=1000");

        String val1 = ttlState.get("key1", 2000L);
        System.out.println("Get at t=2000: " + val1); // Should exist

        String val2 = ttlState.get("key1", 4000L);
        System.out.println("Get at t=4000: " + val2); // Should be expired

        // Test 4: List state
        System.out.println("\n--- Test 4: List State ---");
        StatefulProcessor.ListState<String, String> listState =
            new StatefulProcessor.ListState<>(10000L);

        listState.append("user1", "event1", 1000L);
        listState.append("user1", "event2", 2000L);
        listState.append("user1", "event3", 3000L);

        List<String> events = listState.get("user1", 3500L);
        System.out.println("Events for user1: " + events);
    }
}
```

---

### Pattern 4: Exactly-Once Semantics

**Concept:** Ensure each event is processed exactly once, even with failures.

**Use case:** Financial transactions, billing, critical business logic.

```java
import java.util.*;

/**
 * Exactly-Once Processing
 *
 * Techniques:
 * - Idempotent operations (safe to retry)
 * - Two-phase commit for sinks
 * - Transaction markers in stream
 * - Deduplication with state
 */
public class ExactlyOnceProcessor<K, V> {

    static class Event<K, V> {
        String eventId; // Unique ID for deduplication
        K key;
        V value;
        long timestamp;

        Event(String eventId, K key, V value, long timestamp) {
            this.eventId = eventId;
            this.key = key;
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    static class Transaction {
        String transactionId;
        long timestamp;
        List<String> processedEventIds;
        boolean committed;

        Transaction(String transactionId, long timestamp) {
            this.transactionId = transactionId;
            this.timestamp = timestamp;
            this.processedEventIds = new ArrayList<>();
            this.committed = false;
        }
    }

    /**
     * Deduplication State: Track processed events
     * Time: O(1) per check, Space: O(N) where N=events in window
     */
    static class DeduplicationState {
        private Set<String> processedEventIds;
        private long oldestEventTime;
        private long retentionMs;

        DeduplicationState(long retentionMs) {
            this.processedEventIds = new HashSet<>();
            this.retentionMs = retentionMs;
            this.oldestEventTime = Long.MAX_VALUE;
        }

        /**
         * Check if event already processed
         *
         * TODO: Implement deduplication check
         * 1. Check if eventId exists in set
         * 2. If yes, return true (duplicate)
         * 3. If no, add to set and return false
         */
        public boolean isDuplicate(String eventId) {
            // TODO: Check and add

            return false; // Replace
        }

        /**
         * Cleanup old event IDs
         *
         * TODO: Implement periodic cleanup
         * In production: use time-based eviction or Bloom filter
         */
        public void cleanup(long currentTime) {
            // TODO: Remove event IDs older than retention period
            // Simplified: in reality need timestamps per event
            if (currentTime - oldestEventTime > retentionMs) {
                processedEventIds.clear();
                oldestEventTime = currentTime;
            }
        }

        public int size() {
            return processedEventIds.size();
        }
    }

    /**
     * Idempotent Aggregator: Safe to process same event multiple times
     * Time: O(1) per event, Space: O(K)
     *
     * TODO: Implement idempotent aggregation
     */
    static class IdempotentAggregator<K> {
        // Store: key -> (value, eventId)
        private Map<K, Long> values;
        private Map<K, String> lastEventIds;

        IdempotentAggregator() {
            this.values = new HashMap<>();
            this.lastEventIds = new HashMap<>();
        }

        /**
         * Process event idempotently
         *
         * TODO: Implement idempotent update
         * 1. Check if this exact event was already processed
         * 2. If same eventId, skip (idempotent)
         * 3. If new eventId, update value
         */
        public void process(Event<K, Long> event) {
            // TODO: Check if already processed

            // TODO: Update value (idempotent SET operation)
        }

        public Long getValue(K key) {
            return values.get(key);
        }
    }

    /**
     * Two-Phase Commit Sink: Transactional output
     * Time: O(1) per event, O(N) per commit
     *
     * TODO: Implement 2PC for sink
     */
    static class TransactionalSink<T> {
        private Transaction currentTransaction;
        private List<T> pendingWrites;
        private Set<String> committedTransactions;

        TransactionalSink() {
            this.pendingWrites = new ArrayList<>();
            this.committedTransactions = new HashSet<>();
        }

        /**
         * Begin new transaction
         *
         * TODO: Implement transaction begin
         */
        public void beginTransaction(String txnId) {
            // TODO: Create new transaction
        }

        /**
         * Write to transaction (not committed yet)
         *
         * TODO: Implement transactional write
         */
        public void write(T value, String eventId) {
            // TODO: Add to pending writes
        }

        /**
         * Commit transaction (make writes visible)
         *
         * TODO: Implement commit
         * 1. Check if transaction already committed (idempotent)
         * 2. Flush all pending writes
         * 3. Mark transaction as committed
         */
        public void commit() {
            // TODO: Commit if not already done
            //
            //   if (committedTransactions.contains(currentTransaction.transactionId)) {
            //     // Already committed (idempotent)
            //     return;
            //   }
            //
            //   // Flush writes (in production: write to external system)
            //   System.out.println("COMMIT: " + pendingWrites.size() + " writes");
            //
            //   // Mark as committed
            //   committedTransactions.add(currentTransaction.transactionId);
            //   currentTransaction.committed = true;
        }

        /**
         * Abort transaction (discard writes)
         *
         * TODO: Implement abort
         */
        public void abort() {
            // TODO: Clear pending writes
        }

        public int getPendingCount() {
            return pendingWrites.size();
        }
    }

    /**
     * Checkpoint coordinator: Manage checkpoints for fault tolerance
     * Time: O(S) where S=state size, Space: O(S)
     *
     * TODO: Implement checkpointing
     */
    static class CheckpointCoordinator {
        private long lastCheckpointId;
        private Map<Long, Map<String, Object>> checkpoints;

        CheckpointCoordinator() {
            this.lastCheckpointId = 0;
            this.checkpoints = new HashMap<>();
        }

        /**
         * Trigger checkpoint
         *
         * TODO: Implement checkpoint trigger
         * 1. Generate checkpoint ID
         * 2. Snapshot all state
         * 3. Store checkpoint
         * 4. Return checkpoint ID
         */
        public long triggerCheckpoint(Map<String, Object> state) {
            // TODO: Create checkpoint

            return 0; // Replace
        }

        /**
         * Restore from checkpoint
         *
         * TODO: Implement restore
         */
        public Map<String, Object> restore(long checkpointId) {
            // TODO: Restore state
            return null; // Replace
        }
    }
}
```

**Runnable Client Code:**

```java
import java.util.*;

public class ExactlyOnceClient {

    public static void main(String[] args) {
        System.out.println("=== Exactly-Once Processing ===\n");

        // Test 1: Deduplication
        System.out.println("--- Test 1: Deduplication ---");
        ExactlyOnceProcessor.DeduplicationState dedup =
            new ExactlyOnceProcessor.DeduplicationState(10000L);

        String[] eventIds = {"evt1", "evt2", "evt1", "evt3", "evt2"};
        for (String id : eventIds) {
            boolean isDup = dedup.isDuplicate(id);
            System.out.println("Event " + id + ": " +
                (isDup ? "DUPLICATE" : "NEW"));
        }
        System.out.println("Unique events tracked: " + dedup.size());

        // Test 2: Idempotent aggregation
        System.out.println("\n--- Test 2: Idempotent Operations ---");
        ExactlyOnceProcessor.IdempotentAggregator<String> aggregator =
            new ExactlyOnceProcessor.IdempotentAggregator<>();

        List<ExactlyOnceProcessor.Event<String, Long>> events = Arrays.asList(
            new ExactlyOnceProcessor.Event<>("e1", "user1", 100L, 1000L),
            new ExactlyOnceProcessor.Event<>("e2", "user1", 200L, 2000L),
            new ExactlyOnceProcessor.Event<>("e1", "user1", 100L, 2500L), // Duplicate
            new ExactlyOnceProcessor.Event<>("e3", "user2", 50L, 3000L)
        );

        for (ExactlyOnceProcessor.Event<String, Long> event : events) {
            System.out.println("Processing: " + event.eventId);
            aggregator.process(event);
            System.out.println("  user1: " + aggregator.getValue("user1"));
            System.out.println("  user2: " + aggregator.getValue("user2"));
        }

        // Test 3: Transactional sink
        System.out.println("\n--- Test 3: Transactional Sink ---");
        ExactlyOnceProcessor.TransactionalSink<String> sink =
            new ExactlyOnceProcessor.TransactionalSink<>();

        // Transaction 1
        sink.beginTransaction("txn1");
        sink.write("value1", "e1");
        sink.write("value2", "e2");
        System.out.println("Pending writes: " + sink.getPendingCount());
        sink.commit();

        // Transaction 2 (with abort)
        sink.beginTransaction("txn2");
        sink.write("value3", "e3");
        System.out.println("Pending writes: " + sink.getPendingCount());
        sink.abort();
        System.out.println("After abort: " + sink.getPendingCount());

        // Test 4: Checkpointing
        System.out.println("\n--- Test 4: Checkpointing ---");
        ExactlyOnceProcessor.CheckpointCoordinator coordinator =
            new ExactlyOnceProcessor.CheckpointCoordinator();

        Map<String, Object> state1 = new HashMap<>();
        state1.put("counter", 100);
        state1.put("sum", 500L);

        long cp1 = coordinator.triggerCheckpoint(state1);
        System.out.println("Created checkpoint: " + cp1);

        // Modify state
        state1.put("counter", 200);
        long cp2 = coordinator.triggerCheckpoint(state1);
        System.out.println("Created checkpoint: " + cp2);

        // Restore
        Map<String, Object> restored = coordinator.restore(cp1);
        System.out.println("Restored state: " + restored);
    }
}
```

!!! info "Loop back"
    Now that you have implemented all four patterns, return to the **Quick Quiz** at the top of this page. Fill in the "Verified after learning" fields. Pay particular attention to the state-size calculation — did your answer about unbounded state match what you saw in Pattern 3? Return to the **ELI5** section and complete your one-sentence explanations.

---

## Before/After: Why Stream Processing Matters

!!! note "Key insight"
    The critical difference between batch and stream processing is not throughput — it is **when results are available**. Stream processing produces output continuously as windows close, while batch processing waits for the entire dataset to accumulate before computing anything.

**Your task:** Compare batch processing vs stream processing to understand the impact.

### Example: Real-Time Analytics

**Problem:** Calculate page views per minute for a website getting 10K events/second.

#### Approach 1: Batch Processing (Traditional)

```java
// Batch processing - Process accumulated data every minute
public class BatchAnalytics {

    private List<Event> eventBuffer = new ArrayList<>();

    public void collectEvent(Event event) {
        eventBuffer.add(event);
    }

    // Runs every 60 seconds
    public Map<String, Long> computePageViews() {
        Map<String, Long> counts = new HashMap<>();

        // Process all accumulated events
        for (Event event : eventBuffer) {
            counts.merge(event.page, 1L, Long::sum);
        }

        // Clear buffer for next batch
        eventBuffer.clear();

        return counts;
    }
}
```

**Analysis:**

- **Latency:** 30-60 seconds average (must wait for batch to complete)
- **Memory:** All events in 1 minute = 10K/sec × 60 = 600K events in memory
- **Throughput:** High (process all at once)
- **Real-time:** No - results delayed by up to 60 seconds
- **Use case:** Reports, ETL jobs, historical analysis

**Timeline visualization:**

```
Events:     |-------- 60 seconds of collection --------|
Processing:                                              [Compute] → Results at T+60s
User sees:                                               ↑
                                                    Results 60s old
```

#### Approach 2: Stream Processing (Real-Time)

```java
// Stream processing - Continuous windowing
public class StreamAnalytics {

    private Map<Long, Map<String, Long>> windows = new TreeMap<>();
    private long windowSize = 60_000; // 60 seconds

    public void processEvent(Event event) {
        // Immediate assignment to window
        long windowStart = (event.timestamp / windowSize) * windowSize;

        // Update count immediately
        windows.computeIfAbsent(windowStart, k -> new HashMap<>())
               .merge(event.page, 1L, Long::sum);

        // Emit results when window closes
        long currentTime = System.currentTimeMillis();
        closeCompletedWindows(currentTime);
    }

    private void closeCompletedWindows(long currentTime) {
        // Windows that ended more than watermark delay ago
        long watermark = currentTime - 5000; // 5s delay tolerance

        windows.entrySet().removeIf(entry -> {
            long windowEnd = entry.getKey() + windowSize;
            if (windowEnd < watermark) {
                emitResults(entry.getKey(), entry.getValue());
                return true; // Remove closed window
            }
            return false;
        });
    }

    private void emitResults(long windowStart, Map<String, Long> counts) {
        // Results available immediately when window closes
        System.out.println("Window [" + windowStart + "]: " + counts);
    }
}
```

**Analysis:**

- **Latency:** 5-10 seconds (watermark delay + processing)
- **Memory:** Only active windows = ~2 windows × events = ~100K events in memory
- **Throughput:** Same (10K events/second)
- **Real-time:** Yes - results within seconds
- **Use case:** Dashboards, alerting, fraud detection

**Timeline visualization:**

```
Events:     |--10s--|--10s--|--10s--|--10s--|--10s--|--10s--|
Windows:    [-------- Window 0-60s --------]
Processing:                                  ↑
Results:                                     Results at T+5s
User sees:                                   ↑
                                        Results ~5s old
```

#### Performance Comparison

| Metric                     | Batch (60s)    | Stream (Real-Time) | Improvement      |
|----------------------------|----------------|--------------------|------------------|
| **Latency to see results** | 30-60 seconds  | 5-10 seconds       | **6-10x faster** |
| **Memory (peak)**          | 600K events    | 100K events        | **6x less**      |
| **Staleness of data**      | Up to 60s old  | Up to 5s old       | **12x fresher**  |
| **Throughput**             | 10K events/sec | 10K events/sec     | Same             |

#### Real-World Impact: Fraud Detection Example

**Batch approach:**

```

10:00:00 - Fraudulent transaction occurs
10:00:05 - 3 more suspicious transactions

10:00:45 - 5 more transactions (pattern clear)
10:01:00 - Batch job runs, detects fraud

10:01:05 - Alert sent, account frozen

Total: 9 fraudulent transactions, $4,500 loss
Detection delay: 65 seconds
```

**Stream approach:**

```

10:00:00 - Fraudulent transaction occurs
10:00:05 - 3 more suspicious transactions

10:00:10 - Pattern detected (window closed at watermark)
10:00:11 - Alert sent, account frozen

Total: 4 fraudulent transactions, $2,000 loss
Detection delay: 11 seconds
```

**Impact:** Stream processing caught fraud **54 seconds faster**, preventing **$2,500 in losses**.

#### Why Does Stream Processing Win?

**Key insight to understand:**

Batch processing treats time in discrete chunks:

```
Batch 1: [0s ────────────────── 60s] → Process → Wait
Batch 2: [60s ──────────────── 120s] → Process → Wait
```

Stream processing treats time continuously:

```
Events: ─•──•─•───•──•─•──•─•──•─→ (continuous)
Windows: [─────────] [─────────]
Results:     ↑            ↑
         (immediate)  (immediate)
```

**Your calculation:**

- For 1M events/second, batch processing with 5-minute windows needs _____ GB memory
- Stream processing with 1-minute windows needs _____ GB memory
- Memory savings: <span class="fill-in">_____</span> times less

#### When Batch Processing Is Still Better

**Batch wins when:**

1. **Historical analysis:** Processing months of data
2. **Complex joins:** Multiple large datasets
3. **Cost-sensitive:** Pay per compute hour (batch cheaper)
4. **No urgency:** Daily reports, weekly summaries

**After implementing, explain in your own words:**

<div class="learner-section" markdown>

- Why does stream processing use less memory? <span class="fill-in">[Your answer]</span>
- What's the trade-off between latency and accuracy with watermarks? <span class="fill-in">[Your answer]</span>
- When would you still choose batch processing? <span class="fill-in">[Your answer]</span>

</div>

---

## Common Misconceptions

!!! warning "Misconception 1: A larger watermark delay always gives more accurate results"
    A watermark delay controls how long the system waits for late-arriving events before closing a window. A very large delay does reduce data loss from late events, but it also increases the **end-to-end latency** for every window result — including results that had no late data. The right delay is the smallest value that covers the typical skew in your source data, not the maximum possible skew.

!!! warning "Misconception 2: Exactly-once processing means events are never duplicated in the network"
    Exactly-once semantics refers to the **effect** on state and output, not to how many times messages physically travel over the wire. The implementation typically sends some messages multiple times (at-least-once delivery) and uses idempotency or deduplication to ensure the state machine transitions exactly once per logical event. If your sink is not idempotent, exactly-once at the processor level does not protect against duplicate output.

!!! warning "Misconception 3: Stateless stream processing has no memory growth"
    Stateless operators (map, filter) do not accumulate per-key state, so they have constant memory usage regardless of throughput. However, windowing itself is stateful — each open window stores partial aggregates. A pipeline with many windows, large window sizes, or high key cardinality can still grow memory substantially even if you consider the individual operators "stateless."

---

## Decision Framework

<div class="learner-section" markdown>

**Your task:** Build decision trees for when to use each stream processing pattern.

### Question 1: What type of windowing do you need?

Answer after implementation:

**Use Tumbling Window when:**

- Fixed time boundaries: <span class="fill-in">[Every hour, every day]</span>
- Non-overlapping: <span class="fill-in">[Each event in exactly one window]</span>
- Simple aggregation: <span class="fill-in">[Count, sum per time period]</span>
- Example: <span class="fill-in">[Hourly sales reports, daily active users]</span>

**Use Sliding Window when:**

- Moving average: <span class="fill-in">[Last N minutes]</span>
- Overlapping periods: <span class="fill-in">[Need smooth transitions]</span>
- Real-time dashboards: <span class="fill-in">[Updated frequently]</span>
- Example: <span class="fill-in">[5-minute average updated every 30 seconds]</span>

**Use Session Window when:**

- User activity: <span class="fill-in">[Group by engagement sessions]</span>
- Variable length: <span class="fill-in">[Based on inactivity]</span>
- Burst detection: <span class="fill-in">[Cluster related events]</span>
- Example: <span class="fill-in">[User browsing sessions, click streams]</span>

### Question 2: How do you handle late data?

**Use Watermarks when:**

- Bounded lateness: <span class="fill-in">[Most events arrive within X seconds]</span>
- Completeness needed: <span class="fill-in">[Want accurate results]</span>
- Can tolerate delay: <span class="fill-in">[Results can wait for late data]</span>

**Allow Lateness when:**

- Some late arrivals: <span class="fill-in">[Network delays, mobile sync]</span>
- Update results: <span class="fill-in">[Can emit corrections]</span>
- Balance accuracy/latency: <span class="fill-in">[Wait a bit, not forever]</span>

**Drop Late Data when:**

- Strict latency: <span class="fill-in">[Need real-time results]</span>
- Rare late arrivals: <span class="fill-in">[< 1% of events]</span>
- Approximate OK: <span class="fill-in">[Metrics, dashboards]</span>

### Question 3: Do you need state?

**Stateless processing when:**

- Pure transformations: <span class="fill-in">[map, filter]</span>
- No aggregation: <span class="fill-in">[Just routing events]</span>
- No joins: <span class="fill-in">[Single stream]</span>
- Maximum throughput: <span class="fill-in">[No state overhead]</span>

**Stateful processing when:**

- Aggregations: <span class="fill-in">[count, sum, average]</span>
- Joins: <span class="fill-in">[Combine multiple streams]</span>
- Enrichment: <span class="fill-in">[Add reference data]</span>
- Session tracking: <span class="fill-in">[User state across events]</span>

### Question 4: What consistency level?

**At-most-once when:**

- Monitoring/Metrics: <span class="fill-in">[Losing some data OK]</span>
- Maximum throughput: <span class="fill-in">[No overhead]</span>
- Non-critical: <span class="fill-in">[Dashboards, alerts]</span>

**At-least-once when:**

- Idempotent operations: <span class="fill-in">[Safe to retry]</span>
- Can deduplicate: <span class="fill-in">[Downstream handles duplicates]</span>
- Good balance: <span class="fill-in">[Performance + reliability]</span>

**Exactly-once when:**

- Financial: <span class="fill-in">[Money, billing, payments]</span>
- Critical business logic: <span class="fill-in">[Inventory, orders]</span>
- Compliance: <span class="fill-in">[Audit trails]</span>

### Your Decision Tree

Build this after solving practice scenarios:
```mermaid
flowchart LR
    Start["Stream Processing Pattern Selection"]

    Q1{"What's the data arrival pattern?"}
    Start --> Q1
    N2["Simple windowing"]
    Q1 -->|"In-order,<br/>low latency"| N2
    N3["Watermarks + allowed lateness"]
    Q1 -->|"Out-of-order,<br/>bounded"| N3
    N4["Session windows or approximation"]
    Q1 -->|"Out-of-order,<br/>unbounded"| N4
    Q5{"What operations do you need?"}
    Start --> Q5
    N6["Windowing only"]
    Q5 -->|"Simple aggregation"| N6
    N7["Stateful processing"]
    Q5 -->|"Cross-event logic"| N7
    N8["Stream joins"]
    Q5 -->|"Multiple streams"| N8
    N9["Stateful with reference data"]
    Q5 -->|"Enrichment"| N9
    Q10{"What's the consistency requirement?"}
    Start --> Q10
    N11["At-most-once"]
    Q10 -->|"Best effort"| N11
    N12["At-least-once + dedup"]
    Q10 -->|"No duplicates OK"| N12
    N13["Full transactional processing"]
    Q10 -->|"Exactly-once"| N13
    Q14{"What's the latency requirement?"}
    Start --> Q14
    N15["Drop late data, smaller windows"]
    Q14 -->|"Sub-second"| N15
    N16["Watermarks with small lateness"]
    Q14 -->|"Seconds"| N16
    N17["Large lateness window, accurate results"]
    Q14 -->|"Minutes"| N17
```

</div>

---

## Practice

<div class="learner-section" markdown>

### Scenario 1: Real-Time Analytics Dashboard

**Requirements:**

- Track page views per minute (updated every 10 seconds)
- Show top pages in last 5 minutes
- Handle 100K events/second
- Mobile apps may sync late data (up to 30s delay)
- Display updated immediately

**Your design:**

Windowing strategy: <span class="fill-in">[Tumbling, Sliding, or Session?]</span>

Reasoning:

- Window type: <span class="fill-in">[Fill in]</span>
- Window size: <span class="fill-in">[Fill in]</span>
- Slide interval: <span class="fill-in">[Fill in]</span>
- Why this choice: <span class="fill-in">[Fill in]</span>

Late data handling: <span class="fill-in">[How to handle 30s delayed mobile events?]</span>

- Watermark strategy: <span class="fill-in">[Fill in]</span>
- Allowed lateness: <span class="fill-in">[Fill in]</span>
- Trade-offs: <span class="fill-in">[Fill in]</span>

State requirements: <span class="fill-in">[What state do you need?]</span>

- Per-key state: <span class="fill-in">[Fill in]</span>
- State backend: <span class="fill-in">[In-memory or RocksDB?]</span>
- TTL: <span class="fill-in">[Fill in]</span>

### Scenario 2: Fraud Detection System

**Requirements:**

- Detect suspicious patterns in real-time
- Multiple failed logins within 1 minute
- Transactions from different countries < 10 minutes apart
- Must detect within 2 seconds of last event
- No false negatives (can't miss fraud)

**Your design:**

Pattern detection: <span class="fill-in">[How to detect patterns across events?]</span>

- Windowing: <span class="fill-in">[Fill in]</span>
- State needed: <span class="fill-in">[Fill in]</span>
- Join strategy: <span class="fill-in">[Fill in]</span>

Consistency: <span class="fill-in">[At-most-once, at-least-once, or exactly-once?]</span>

- Choice: <span class="fill-in">[Fill in]</span>
- Why: <span class="fill-in">[Fill in]</span>
- Implementation: <span class="fill-in">[Deduplication? Transactions?]</span>

Latency: <span class="fill-in">[How to meet 2-second requirement?]</span>

- Watermark strategy: <span class="fill-in">[Fill in]</span>
- Trade-offs: <span class="fill-in">[Accuracy vs speed]</span>

### Scenario 3: IoT Sensor Aggregation

**Requirements:**

- 10K sensors sending readings every 10 seconds
- Compute average, min, max per sensor per minute
- Sensors have unreliable networks (late data common)
- Some sensors offline for hours, then send batch
- Store aggregates in database (no duplicates)

**Your design:**

Windowing: <span class="fill-in">[Which type and why?]</span>

- Window type: <span class="fill-in">[Fill in]</span>
- Size: <span class="fill-in">[Fill in]</span>
- Reasoning: <span class="fill-in">[Fill in]</span>

Late data: <span class="fill-in">[How to handle hours-late data?]</span>

- Watermark strategy: <span class="fill-in">[Fill in]</span>
- Allowed lateness: <span class="fill-in">[Fill in]</span>
- Very late data: <span class="fill-in">[Drop or reprocess?]</span>

State management: <span class="fill-in">[How to manage state for 10K sensors?]</span>

- State size: <span class="fill-in">[Estimate per sensor]</span>
- TTL: <span class="fill-in">[How long to keep state?]</span>
- Cleanup: <span class="fill-in">[When to purge old state?]</span>

Output: <span class="fill-in">[How to avoid duplicate writes to database?]</span>

- Strategy: <span class="fill-in">[Idempotent writes? Deduplication? Transactions?]</span>
- Implementation: <span class="fill-in">[Fill in]</span>

</div>

---

## Test Your Understanding

Answer these questions without looking at your implementation. They are designed to probe understanding, not recall.

1. **You set `allowedLateness = 0` and observe that some events are unexpectedly dropped even though they arrive only milliseconds after their window closes. Why does this happen, and what value would you choose instead?**

2. **A session window with a 5-second gap creates 10 sessions for a given user over 1 hour. If you reduce the gap to 2 seconds, will there be more or fewer sessions? Explain why.**

3. **Your stateful processor tracks a running sum per user ID with a 10-minute TTL. After running for 24 hours, you observe that memory is still growing. What is the most likely cause, and how would you verify it?**

4. **A stream join buffers left-stream events waiting for matching right-stream events. You configure a join window of 30 seconds but set state TTL to 20 seconds. What problem will you observe, and what is the correct TTL value?**

5. **Design decision: You are building a billing pipeline that charges customers based on usage events. A downstream payment service is idempotent (accepts a charge ID and ignores duplicates). Should you use at-least-once or exactly-once semantics at the stream processor layer? Justify your choice with a specific trade-off argument.**
