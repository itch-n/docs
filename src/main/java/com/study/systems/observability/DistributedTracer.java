package com.study.systems.observability;

import java.util.*;
import java.time.*;
import java.util.concurrent.*;

/**
 * Distributed Tracing: Track requests across services
 *
 * Concepts:
 * - Trace: End-to-end request flow
 * - Span: Single operation within a trace
 * - Context: Trace ID + Span ID propagated across services
 */
public class DistributedTracer {

    /**
     * Trace context: Propagated across service boundaries
     *
     * TODO: Define trace context structure
     */
    static class TraceContext {
        String traceId;      // Unique ID for entire trace
        String spanId;       // Current span ID
        String parentSpanId; // Parent span ID (null for root)

        TraceContext(String traceId, String spanId, String parentSpanId) {
            this.traceId = traceId;
            this.spanId = spanId;
            this.parentSpanId = parentSpanId;
        }

        /**
         * Create root trace context
         *
         * TODO: Generate new trace ID
         */
        public static TraceContext createRoot() {
            // TODO: Generate unique trace ID
            return null; // Replace
        }

        /**
         * Create child context for new span
         *
         * TODO: Generate child span ID
         */
        public TraceContext createChild() {
            // TODO: Keep same trace ID, new span ID
            return null; // Replace
        }
    }

    /**
     * Span: Represents single operation
     * Time: O(1) for all operations
     *
     * TODO: Define span structure
     */
    static class Span {
        String traceId;
        String spanId;
        String parentSpanId;
        String operationName;
        long startTimeMicros;
        long endTimeMicros;
        Map<String, String> tags;
        List<String> logs;

        Span(TraceContext context, String operationName) {
            this.traceId = context.traceId;
            this.spanId = context.spanId;
            this.parentSpanId = context.parentSpanId;
            this.operationName = operationName;
            this.startTimeMicros = System.nanoTime() / 1000;
            this.tags = new HashMap<>();
            this.logs = new ArrayList<>();
        }

        /**
         * Add tag to span (metadata)
         *
         * TODO: Add span tag
         */
        public void setTag(String key, String value) {
            // TODO: Add to tags map
        }

        /**
         * Log event within span
         *
         * TODO: Add log entry
         */
        public void log(String message) {
            // TODO: Add timestamped log
        }

        /**
         * Finish span (record end time)
         *
         * TODO: Record end time
         */
        public void finish() {
            // TODO: Set end time
        }

        public long getDurationMicros() {
            return endTimeMicros - startTimeMicros;
        }
    }

    /**
     * Tracer: Creates and manages spans
     */
    private final String serviceName;
    private final ThreadLocal<Deque<Span>> activeSpans;
    private final List<Span> completedSpans;

    public DistributedTracer(String serviceName) {
        this.serviceName = serviceName;
        this.activeSpans = ThreadLocal.withInitial(ArrayDeque::new);
        this.completedSpans = new CopyOnWriteArrayList<>();
    }

    /**
     * Start new root span
     * Time: O(1)
     *
     * TODO: Create root span
     */
    public Span startSpan(String operationName) {
        // TODO: Create root context and span
        return null; // Replace
    }

    /**
     * Start child span of current active span
     * Time: O(1)
     *
     * TODO: Create child span
     */
    public Span startChildSpan(String operationName) {
        // TODO: Get current span, create child context
        //
        //   Span parent = stack.peek();
        //   TraceContext parentContext = new TraceContext(
        //     parent.traceId, parent.spanId, parent.parentSpanId
        //   );
        //   TraceContext childContext = parentContext.createChild();
        //   Span span = new Span(childContext, operationName);
        //   span.setTag("service", serviceName);
        //   stack.push(span);
        //   return span;
        return null; // Replace
    }

    /**
     * Finish current span
     * Time: O(1)
     *
     * TODO: Finish and record span
     */
    public void finishSpan() {
        // TODO: Pop span from stack, finish it, store it
    }

    /**
     * Get current active span
     * Time: O(1)
     *
     * TODO: Return current span
     */
    public Span getCurrentSpan() {
        // TODO: Peek at top of stack
        return null; // Replace
    }

    /**
     * Find all spans for a trace
     * Time: O(N) where N = total spans
     *
     * TODO: Filter spans by trace ID
     */
    public List<Span> getTrace(String traceId) {
        // TODO: Filter completed spans by trace ID
        return null; // Replace
    }

    /**
     * Build trace tree for visualization
     * Time: O(N) where N = spans in trace
     *
     * TODO: Build parent-child tree
     */
    public String visualizeTrace(String traceId) {
        // TODO: Build tree structure from parent-child relationships
        //
        //   // Group by parent
        //   for (Span span : spans) {
        //     String parentId = span.parentSpanId != null ? span.parentSpanId : "root";
        //     childrenMap.computeIfAbsent(parentId, k -> new ArrayList<>())
        //                .add(span);
        //   }
        //
        //   // Find root and build tree
        //   Span root = spans.stream()
        //     .filter(s -> s.parentSpanId == null)
        //     .findFirst()
        //     .orElse(null);
        //
        //   StringBuilder sb = new StringBuilder();
        //   buildTraceString(root, childrenMap, sb, 0);
        //   return sb.toString();

        return null; // Replace
    }

    private void buildTraceString(Span span, Map<String, List<Span>> childrenMap,
                                   StringBuilder sb, int depth) {
        // TODO: Recursively build tree string
        //
        //   List<Span> children = childrenMap.get(span.spanId);
        //   if (children != null) {
        //     for (Span child : children) {
        //       buildTraceString(child, childrenMap, sb, depth + 1);
        //     }
        //   }
    }

    /**
     * Calculate critical path (longest path in trace)
     * Time: O(N) where N = spans
     *
     * TODO: Find bottleneck in trace
     */
    public List<Span> getCriticalPath(String traceId) {
        // TODO: DFS to find longest path
        return null; // Replace
    }


    // --- demo (moved from DistributedTracerClient) ---

public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Distributed Tracing ===\n");

        DistributedTracer tracer = new DistributedTracer("order-service");

        // Test 1: Simple trace
        System.out.println("--- Test 1: Simple Trace ---");
        Span rootSpan = tracer.startSpan("process_order");
        rootSpan.setTag("order_id", "12345");
        rootSpan.log("Order validation started");

        Thread.sleep(10);
        rootSpan.log("Order validated");
        tracer.finishSpan();

        System.out.println("Root span duration: " +
            rootSpan.getDurationMicros() / 1000.0 + "ms");

        // Test 2: Parent-child spans
        System.out.println("\n--- Test 2: Parent-Child Spans ---");
        Span apiSpan = tracer.startSpan("api_request");
        apiSpan.setTag("endpoint", "/api/checkout");
        apiSpan.log("Request received");

        Thread.sleep(5);

        Span dbSpan = tracer.startChildSpan("database_query");
        dbSpan.setTag("query", "SELECT * FROM orders");
        dbSpan.log("Query started");
        Thread.sleep(15);
        dbSpan.log("Query completed");
        tracer.finishSpan(); // Finish DB span

        Span cacheSpan = tracer.startChildSpan("cache_lookup");
        cacheSpan.setTag("key", "user:123");
        Thread.sleep(2);
        tracer.finishSpan(); // Finish cache span

        apiSpan.log("Request completed");
        tracer.finishSpan(); // Finish API span

        String traceId = apiSpan.traceId;
        System.out.println("Trace ID: " + traceId);
        System.out.println("API span duration: " + apiSpan.getDurationMicros() / 1000.0 + "ms");
        System.out.println("DB span duration: " + dbSpan.getDurationMicros() / 1000.0 + "ms");
        System.out.println("Cache span duration: " + cacheSpan.getDurationMicros() / 1000.0 + "ms");

        // Test 3: Trace visualization
        System.out.println("\n--- Test 3: Trace Visualization ---");
        String visualization = tracer.visualizeTrace(traceId);
        if (visualization != null) {
            System.out.println(visualization);
        } else {
            System.out.println("(Trace visualization not yet implemented)");
        }

        // Test 4: Multiple traces
        System.out.println("\n--- Test 4: Multiple Traces ---");
        Span trace1 = tracer.startSpan("operation_1");
        Thread.sleep(5);
        tracer.finishSpan();

        Span trace2 = tracer.startSpan("operation_2");
        Thread.sleep(8);
        tracer.finishSpan();

        System.out.println("Trace 1 ID: " + trace1.traceId);
        System.out.println("Trace 2 ID: " + trace2.traceId);
        System.out.println("Different traces: " + !trace1.traceId.equals(trace2.traceId));
    }
}
