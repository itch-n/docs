package com.study.systems.eventsourcing;

import java.util.List;

/**
 * Core event-sourcing abstractions, grouped as a single unit.
 */
public class EventSourcing {

    private EventSourcing() {}

    // ---------------------------------------------------------------------------
    // Core domain types
    // ---------------------------------------------------------------------------

    /**
     * Marker interface for all domain events.
     * Every event carries the aggregate ID and the version it was appended at.
     */
    public interface DomainEvent {
        String aggregateId();
        long version();
        long occurredAt(); // epoch millis
    }

    /**
     * Marker interface for all commands.
     * A command is a request to change state; it may be rejected.
     */
    public interface Command {
        String aggregateId();
    }

    // ---------------------------------------------------------------------------
    // EventStore — the append-only log
    // ---------------------------------------------------------------------------

    /**
     * The event store is the sole source of truth.
     *
     * Implementations may use a relational table, EventStoreDB, Kafka, or any
     * durable append-only log.
     */
    public interface EventStore {

        /**
         * Append events to a stream.
         *
         * @param aggregateId  the stream identifier
         * @param events       ordered list of events to append
         * @param expectedVersion  the version the caller believes the stream is at;
         *                         throws OptimisticConcurrencyException if the
         *                         actual version differs (another writer raced ahead)
         *
         * TODO: Implement append
         * - Verify that the current stream version matches expectedVersion
         * - Assign sequential version numbers to each event
         * - Persist all events atomically (all succeed or all fail)
         * - Publish events to any registered subscribers after commit
         */
        void append(String aggregateId, List<DomainEvent> events, long expectedVersion);

        /**
         * Load all events for a stream, in order.
         *
         * @param aggregateId  the stream identifier
         * @return ordered list of all events; empty list if stream does not exist
         *
         * TODO: Implement load
         * - Return events in append order (by version, ascending)
         * - Return an empty list (not null) if the aggregate has no events
         */
        List<DomainEvent> load(String aggregateId);

        /**
         * Load events starting from a given version (inclusive).
         * Used when loading after a snapshot.
         *
         * @param aggregateId   the stream identifier
         * @param fromVersion   the first version to include in the result
         * @return ordered list of events at version >= fromVersion
         *
         * TODO: Implement partial load
         * - Return only events where version >= fromVersion
         * - Return in ascending version order
         */
        List<DomainEvent> loadFrom(String aggregateId, long fromVersion);
    }

    // ---------------------------------------------------------------------------
    // Aggregate — the write-side consistency boundary
    // ---------------------------------------------------------------------------

    /**
     * Abstract base for event-sourced aggregates.
     *
     * An aggregate is rebuilt by replaying its events. It enforces business
     * invariants and emits new events when a command is accepted.
     *
     * Usage pattern:
     *   1. Load events from EventStore
     *   2. Call replayAll(events) to rebuild state
     *   3. Call handleCommand(cmd) to produce new events
     *   4. Append the returned events to EventStore
     */
    public abstract static class Aggregate {

        private String id;
        private long version = 0;

        // Events raised by the most recent handleCommand call, not yet persisted.
        // TODO: initialise this field to an empty mutable list
        private List<DomainEvent> pendingEvents;

        public String getId() { return id; }
        public long getVersion() { return version; }
        public List<DomainEvent> getPendingEvents() { return pendingEvents; }

        /**
         * Replay a single event to rebuild aggregate state.
         * Must NOT throw for unknown event types — be tolerant of future event versions.
         *
         * TODO: Implement in each concrete aggregate subclass
         * - Switch on event type and update internal state fields
         * - Increment the version counter
         * - Do NOT enforce invariants here — apply is called during both
         *   initial load and command handling; only handleCommand enforces rules
         */
        protected abstract void apply(DomainEvent event);

        /**
         * Replay a sequence of events (used when loading from the event store).
         *
         * TODO: Implement replayAll
         * - Call apply(event) for each event in order
         * - After replay, version should equal the version of the last event
         */
        public void replayAll(List<DomainEvent> events) {
            // TODO: iterate events and call apply on each
        }

        /**
         * Handle an inbound command and return the events it produces.
         * Returns an empty list if the command is a no-op.
         * Throws a domain exception if the command violates an invariant.
         *
         * TODO: Implement in each concrete aggregate subclass
         * - Validate the command against current state
         * - Construct new events (do not persist them — that is the caller's job)
         * - Call apply() on each new event to update in-memory state
         * - Add new events to pendingEvents
         * - Return the list of new events
         */
        public abstract List<DomainEvent> handleCommand(Command command);

        /**
         * Raise a new event: apply it locally and queue it for persistence.
         * Concrete subclasses call this inside handleCommand.
         *
         * TODO: Implement raiseEvent
         * - Call apply(event) to update in-memory state immediately
         * - Add event to pendingEvents
         */
        protected void raiseEvent(DomainEvent event) {
            // TODO: apply event to self
            // TODO: add to pendingEvents
        }
    }

    // ---------------------------------------------------------------------------
    // Projection — the read-side materialised view
    // ---------------------------------------------------------------------------

    /**
     * A projection consumes the event stream and maintains a queryable read model.
     *
     * Projections are eventually consistent with the write model.
     * They must be idempotent: replaying the same event twice must not corrupt state.
     */
    public interface Projection {

        /**
         * Process a single domain event and update the read model.
         *
         * TODO: Implement in each concrete projection
         * - Dispatch on event type to the appropriate handler method
         * - Update the projection store (database table, cache, etc.)
         * - Must be idempotent: if this event was already applied, do not double-count
         * - Record the event version as the new checkpoint after successful update
         *
         * @param event   the event to process
         */
        void on(DomainEvent event);

        /**
         * Return the version of the last event successfully applied.
         * Used to resume from a checkpoint after restart.
         *
         * TODO: Implement getCheckpoint
         * - Load the persisted checkpoint from durable storage
         * - Return 0 if no checkpoint exists (projection has never run)
         */
        long getCheckpoint();

        /**
         * Reset the projection to empty state.
         * Called at the start of a full rebuild.
         *
         * TODO: Implement reset
         * - Delete or truncate the projection store
         * - Reset the checkpoint to 0
         */
        void reset();
    }

    // ---------------------------------------------------------------------------
    // SnapshotStore — optimisation layer
    // ---------------------------------------------------------------------------

    /**
     * Stores and retrieves aggregate snapshots to bound replay time.
     */
    public interface SnapshotStore {

        /**
         * Save a snapshot of aggregate state at a specific version.
         *
         * @param aggregateId  stream identifier
         * @param version      the event version this snapshot was taken after
         * @param state        serialised aggregate state (JSON, Protobuf, etc.)
         *
         * TODO: Implement save
         * - Persist aggregateId, version, and serialised state atomically
         * - Overwrite any existing snapshot for this aggregate
         */
        void save(String aggregateId, long version, byte[] state);

        /**
         * Load the most recent snapshot for an aggregate.
         * Returns null if no snapshot exists.
         *
         * TODO: Implement load
         * - Return the snapshot with the highest version for this aggregateId
         * - Return null (not an exception) if no snapshot exists
         */
        Snapshot load(String aggregateId);

        /**
         * Container for a snapshot read result.
         */
        record Snapshot(String aggregateId, long version, byte[] state) {}
    }
}
