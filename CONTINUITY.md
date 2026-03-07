# Session Continuity

## What Was Done

### Completed
1. **Learner-section wrappers** — added `<div class="learner-section" markdown>` / `</div>` to all Decision Framework and Practice sections across all 33 files (via `/scripts/add_learner_wrappers.py`)
2. **Review Checklists** — added to `03-networking-fundamentals.md`, `04-search-and-indexing.md`, `16-consensus-patterns.md`
3. **index.md count** — fixed `(17 Topics)` → `(16 Topics)` for Systems Design
4. **DSA pruning** — deleted tries, DP 2D, backtracking, advanced topics; merged trees-traversals + trees-recursion into `06-trees.md`; renumbered DSA files to 01–12; renamed DP 1D → `12-dynamic-programming.md`
5. **Networking trim (~30% done)** — `03-networking-fundamentals.md`: cut Topic 6 (L4/L7 Load Balancing), cut Case Studies section, cut Decision Framework Question 4. 1041→870 lines.
6. **DSA restored** — created `13-prefix-sums.md` and `14-intervals.md` from scratch (sourced from deleted `17-advanced-topics.md` git history); updated mkdocs.yml and index.md to 14 DSA topics
7. **Networking L4/L7 cleanup** — removed residual L4/L7 references from tagline, learning objectives, ELI5 item 6, Quick Quiz Scenario 1, Practice Scenario 1 load-balancing sub-section, TYU Q4, and Review Checklist
8. **Security patterns trim** — `07-security-patterns.md`: removed Before/After section (~327 lines). 1376→1049 lines.
9. **Concurrency patterns trim** — `10-concurrency-patterns.md`: removed Patterns 5–8 (Non-Blocking I/O, Virtual Threads, Coroutines, Reactive Streams); cleaned Learning Objectives, ELI5, Decision Framework. 3685→2515 lines.
10. **Stream processing trim** — `13-stream-processing.md`: cut Patterns 2–4 (Watermarks, Stateful, Exactly-Once); cleaned all P2–4 references from Quick Quiz, Decision Framework, Practice, TYU. 1807→713 lines.
11. **Observability trim** — `14-observability.md`: removed AtomicDouble helper, MetricsClient runnable code (P1), parseLog/filterByLevel/findByContext helpers, StructuredLoggerClient runnable code (P2). 2056→1851 lines.

12. **L4/L7 gap fix** — `03-networking-fundamentals.md`: added forward-reference `!!! info` note after TLS section; `09-load-balancing.md`: added full `## Layer 4 vs Layer 7` section with comparison table, Decision Framework Q4, and TYU Q6.
13. **Structural improvements (all 30 files)** — applied 3 improvements across all 16 systems + 14 DSA files:
    - **Failure-mode questions** — 2 scenario-specific failure-mode questions added inside each Practice `<div class="learner-section">` block
    - **TYU rubrics** — `??? success "Rubric"` admonition added under every TYU question (5–6 per file), with 3 concrete technical insights each
    - **Connected Topics** — `## Connected Topics` section with `!!! info` admonition added at the end of every file, linking to 2–3 related topics with one-sentence connection descriptions
    - All 30 files verified div-balanced after edits
    - Item 2 (worked examples in Decision Frameworks) was dropped — conflicts with fill-in-first pedagogy; Case Studies already serve that function

### Skipped (user said "don't touch")
- `06-api-design.md` — originally skipped for trimming; structural improvements (failure modes, rubrics, Connected Topics) were applied as normal

### User Preferences (apply to all future edits)
- **Do NOT touch Case Studies sections** — user wants to keep them
- **Item 2 (worked examples in Decision Frameworks) is dropped** — conflicts with fill-in pedagogy

### Still Pending
None.

## Systems Trimming Rationale (staff backend interview lens)

The guiding question: *what does a staff backend engineer actually need to know for system design interviews?* Staff-level interviews focus on design tradeoffs, failure modes, and operational reasoning — not implementation minutiae or junior-level mechanics.

### Files flagged for trimming

| File | Why trim |
|------|----------|
| `03-networking-fundamentals.md` | L4/L7 load balancing is covered in `09-load-balancing.md` — redundant here. Case studies are illustrative fluff, not interview prep. |
| `06-api-design.md` | REST basics (HTTP verbs, status codes) are entry-level knowledge; staff engineers don't get quizzed on them. Idempotency, versioning, pagination tradeoffs are the real interview surface. Verbose before/after examples waste space. |
| `07-security-patterns.md` | Too implementation-heavy — staff interviews ask "how would you design auth for X" not "write JWT validation code". Half the file is boilerplate code. |
| `10-concurrency-patterns.md` | Patterns 5–8 (Non-Blocking I/O, Virtual Threads, Coroutines, Reactive Streams) are Java/language-specific runtime details, not systems-level patterns. A staff interview asks about lock contention, producer-consumer, thread safety — not JVM virtual thread internals. |
| `13-stream-processing.md` | Deep stream processing (watermarks, stateful operators, exactly-once semantics) is specialized knowledge for data platform roles. General backend staff interviews rarely go here. Windowing is sufficient conceptual coverage. |
| `14-observability.md` | Metrics and logging implementation code (how to instrument a class, how to write a log formatter) is ops/SRE territory. Staff backend interviews focus on SLO design, what to measure, tracing architecture — not how to call a metrics library. |

### Files kept as-is (already staff-appropriate)

| File | Why keep |
|------|----------|
| `01-storage-engines.md` | Core systems topic — B+Tree vs LSM is a classic staff interview question |
| `02-row-vs-column-storage.md` | OLTP vs OLAP tradeoffs are directly asked at staff level |
| `04-search-and-indexing.md` | Inverted indexes, ranking, Elasticsearch sharding — all relevant |
| `05-caching-patterns.md` | Cache invalidation, write strategies, eviction — evergreen staff topic |
| `08-rate-limiting.md` | Token bucket vs sliding window is a canonical design question |
| `09-load-balancing.md` | Consistent hashing, health checks — directly asked |
| `11-database-scaling.md` | Replication, sharding, partitioning — high-frequency staff topic |
| `12-message-queues.md` | Queue vs pub/sub, delivery guarantees — directly asked |
| `15-distributed-transactions.md` | Saga pattern, idempotency — staff-level distributed systems |
| `16-consensus-patterns.md` | Raft, leader election, distributed locks — senior/staff systems topic |

## Preservation Rules (apply to all edits)
- Keep ALL `<div class="learner-section" markdown>` / `</div>` wrappers — must stay balanced
- Keep ALL `<span class="fill-in">[Fill in]</span>` placeholders
- Keep ALL `!!! warning`, `??? success`, `!!! tip`, `!!! info` admonitions
- Keep ALL Java code stubs with `// TODO:` comments
- Keep section structure: ELI5, Quick Quiz, Core Implementation, Decision Framework, Practice Scenarios, TYU, Review Checklist

## Current File State
- `docs/dsa/` — 14 files (01–14), all with learner-section wrappers
- `docs/systems/` — 16 files (01–16), all with learner-section wrappers; 4 trimmed this session
- `mkdocs.yml` — updated to reflect 14 DSA topics
- `docs/index.md` — updated to reflect 14 DSA topics, 16 Systems topics
