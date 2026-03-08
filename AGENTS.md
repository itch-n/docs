# AGENTS.md

Context for Claude Code sessions working in this repository.

---

## Project Overview

**itch-n-docs** is an interactive backend engineering study guide published as a static site via MkDocs. The audience is software engineers studying for interviews or deepening systems/algorithms knowledge. Content is structured as fill-in exercises — learners implement first, then explain.

- Site name: "Software Engineering Study Guide"
- Author: Richard
- Deployed to GitHub Pages via GitHub Actions on push to your personal branch (branch name configured in `.github/workflows/deploy.yml`)

---

## Tech Stack

| Layer | Tool |
|-------|------|
| Static site | MkDocs 1.6.1+ with Cinder theme |
| Python runtime | Python 3.13+, managed via `uv` |
| Code examples | Java 25, built with Gradle |
| Testing | JUnit 5 (Jupiter) |
| Diagrams | mkdocs-mermaid2-plugin |
| Markdown extensions | pymdownx (tasklist, details, superfences), tables, toc, md_in_html |
| CI/CD | GitHub Actions → GitHub Pages |

---

## Content Structure

Every topic file follows this section pattern:

1. **Learning Objectives** — bulleted outcomes
2. **`!!! note "Operational reality"`** — theory-vs-production gap, named systems approach
3. **ELI5** — simplified explanation (learner fills in)
4. **Core Implementation / Core Concepts** — code stubs with TODOs and debugging challenges
5. **Before/After** — motivation for the pattern (some files)
6. **Case Studies** — real-world examples (always preserve, never cut)
7. **Common Misconceptions** — `!!! danger` blocks, one per misconception
8. **`!!! warning "When it breaks"`** — scale thresholds and failure conditions
9. **Decision Framework** — when/how to use the concept (learner fills in)
10. **Practice** — scenario questions
11. **Test Your Understanding** — `??? success "Rubric"` self-assessment
12. **Connected Topics** — `<div class="bs-callout bs-callout-info">` (not an admonition — see Standard admonitions section)

Topics live in two sections:

- `docs/systems/` — 20 topics: storage engines, row vs column, networking, search & indexing, caching, API design, security, rate limiting, load balancing, concurrency, database scaling, message queues, event sourcing/CQRS, stream processing, observability, resilience, distributed transactions, consensus, microservices, multi-region
- `docs/dsa/` — 15 topics: two pointers, sliding window, hash tables, linked lists, stacks & queues, trees, binary search, heaps, union-find, graphs, advanced graphs, backtracking, dynamic programming, prefix sums, intervals

Quick reference pages live in `docs/reference/`:

- `docs/reference/when-it-breaks.md` — compiled scale thresholds and failure conditions from all 35 topic files
- `docs/reference/back-of-envelope.md` — hardware primitives, worked derivations, QPS/storage/write scale context
- `docs/reference/symptom-pattern.md` — symptom → pattern lookup for debugging

---

## CSS Conventions

Learner-fillable content uses specific CSS classes defined in `docs/css/custom.css`:

```markdown
<div class="learner-section" markdown>

**Your task:** After implementing, explain the concept.

- Your answer: <span class="fill-in">[Fill in]</span>

</div>
```

| Class | Purpose | Visual |
|-------|---------|--------|
| `.learner-section` | Wraps entire fill-in sections | Yellow/orange background |
| `.fill-in` | Inline blank prompts | Bold orange text |
| `.benchmark-table` | Tables for recording results | Yellow-bordered |
| `.benchmark-table .blank` | Individual blank cells in benchmark tables | Bold orange text, light bg |
| `.learner-prompt` | Individual list item prompts | Yellow background |
| `.code-reference` | Reference/example code | Gray background |

Reference material (code stubs, algorithm explanations, complexity tables) is left unstyled.

---

## Common Commands

See **README.md → Getting Started** for the full command reference (includes Java test commands, benchmark runners, and the `--dirtyreload` flag for fast local dev).

---

## Content Authoring Notes

- New topics: create file in `docs/systems/` or `docs/dsa/`, then register in `mkdocs.yml` nav
- `md_in_html` extension is enabled — `markdown` attribute on divs processes inner Markdown
- See `docs/systems/01-storage-engines.md` as the canonical example of a fully-styled topic

### Standard admonitions

Cinder supports three admonition types: `note` (blue), `warning` (yellow/orange), `danger` (red). Never use `!!! info`, `!!! tip`, `!!! success`, or any other type — they render unstyled.

Four admonition types and one custom div have established placement and purpose:

| Admonition | Type | Placement | Purpose |
|------------|------|-----------|---------|
| `!!! note "Operational reality"` | note | After Learning Objectives `---`, before `## ELI5` | Theory-vs-production gap; named tools and systems |
| `!!! warning "When it breaks"` | warning | After `## Decision Framework` closing `</div>`, before `## Practice` | Mechanism + scale thresholds; explains *why* the break occurs |
| `!!! danger "Misconception: ..."` | danger | In Common Misconceptions section | One block per misconception; use a descriptive title, not "Misconception N:" |
| `!!! warning "Debugging Challenge — ..."` | warning | After code stubs in DSA files | Buggy code for learner to find; answer in `??? success "Answer"` block |

**"Where this topic connects"** uses a custom div, not an admonition — this gives distinct blue callout styling via `bs-callout-info`:

```markdown
<div class="bs-callout bs-callout-info" markdown>

**Where this topic connects**

- **Topic name** — explanation → [Topic name](filename.md)

</div>
```

Do NOT use `!!! note`, `!!! info`, or any other admonition for this section. `!!! info` is unsupported by Cinder and must not be used anywhere in the project.

Collapsible blocks (`??? success "Answer"`, `??? success "Rubric"`) are used for debugging challenge answers and self-assessment rubrics respectively.

For reference pages (`back-of-envelope.md` style), use `??? note "Derivation"` to collapse the arithmetic and keep results scannable at the top level.

### Operational reality — content rules

- Anchor to **named systems and tools** (Redis, PostgreSQL, Kafka, Linux kernel, git), not language-specific APIs
- The voice is direct, slightly contrarian, and names real failure modes
- For DSA files: focus on "where this appears in systems you already work with" — non-obvious production appearances
- For systems files: focus on the gap between the textbook pattern and what actually ships

### When it breaks — content rules

- Explain the *mechanism* first — why the system or algorithm breaks at a structural level — then anchor with a concrete number or threshold
- Both parts are required: mechanism without a number is vague; a number without mechanism is hard to remember
- Format: prose sentences (not bullet lists) within the single admonition block
- For DSA: explain the invariant that breaks (wrong precondition, violated monotonic property, state space explosion), then give the practical consequence
- For systems: explain the chain of causation (what accumulates, what fills, what queues), then give the scale threshold (ops/sec, node count, data volume, latency floor)

### Quick Reference pages — maintenance note

`docs/when-it-breaks.md` and `docs/back-of-envelope.md` are intentionally curated digests, not auto-generated mirrors. Minor drift from individual topic files is acceptable. Update them when making significant changes to a topic's breaking conditions or scale thresholds — not for every small edit.

---

## Markdown Rendering Gotchas

MkDocs uses Python-Markdown, which has stricter whitespace rules than CommonMark. These are **silent failures** — the build passes with no warnings but the output looks wrong in the browser.

### List Formatting — blank line required before lists

Python-Markdown requires a blank line before any list. A list that immediately follows a paragraph or heading (no blank line) renders as plain text, not bullet points.

**Wrong:**
```markdown
Your task: Answer these questions.
- Question 1
- Question 2
```

**Correct:**
```markdown
Your task: Answer these questions.

- Question 1
- Question 2
```

AI-generated markdown frequently omits this blank line. After generating any file with lists, check with:

```bash
grep -n "^- \|^\* " docs/path/to/file.md
# Inspect the lines above each match in your editor
```

### Hard Line Breaks — two trailing spaces required

A single newline between two lines of text is treated as a soft wrap — they render as one continuous paragraph. To force a hard line break (e.g. labelled field pairs like `**Repo:** ...` / `**Used in:** ...`), add **two trailing spaces** at the end of the line that should break.

**Wrong:**
```markdown
**Repo:** itch-n-docs
**Used in:** systems/01-storage-engines.md
```

**Correct:**
```markdown
**Repo:** itch-n-docs
**Used in:** systems/01-storage-engines.md
```

AI-generated markdown often omits trailing spaces on consecutive labelled lines, causing them to flow together. Check structured metadata blocks after generation and add `  ` (two spaces) where needed.

---

## Persisting Learnings

When working in this repo, track patterns worth adding here. A good repo-level learning:

- Would have saved time if known at the start of the session
- Applies to future contributors, not just this specific task
- Is a decision, convention, or gotcha — not a one-off fix
- Isn't already captured in this file

At natural pause points, offer: *"I noticed [X pattern]. Should I add this to AGENTS.md?"*

---

## Editing Conventions (Learned from Past Sessions)

### Large section deletions — use Python, not Edit

The Edit tool fails when `old_string` spans many lines or is very long. For cuts of 50+ lines, use an inline Python script:

```python
with open('docs/systems/file.md', 'r') as f:
    lines = f.readlines()
out = lines[:start_line] + lines[end_line:]  # 0-indexed
with open('docs/systems/file.md', 'w') as f:
    f.writelines(out)
```

Or with content-based markers:

```python
start_idx = content.find('\n### Section to cut')
end_idx = content.find('\n## Next section to keep')
new_content = content[:start_idx] + content[end_idx:]
```

### Runnable client code is trimmable boilerplate

Each implementation pattern may end with a `public class XClient { public static void main(String[] args) {...} }` test harness. These are **not learner content** — they just exercise the TODO stubs. When trimming a file for length, always remove these first (typical savings: 60–100 lines per pattern).

### Case Studies sections — always preserve

User explicitly wants all `## Case Studies:` sections kept in every file. Never remove or shorten them, even when cutting for length.

### After "file has been modified since read" error — re-read before editing

If the Edit tool returns "file has been modified since read," re-read the file first, then retry the edit. This happens after Python-based deletions that change line offsets.

