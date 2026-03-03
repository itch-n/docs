# AGENTS.md

Context for Claude Code sessions working in this repository.

---

## Project Overview

**itch-n-docs** is an interactive backend engineering study guide published as a static site via MkDocs. The audience is software engineers studying for interviews or deepening systems/algorithms knowledge. Content is structured as fill-in exercises — learners implement first, then explain.

- Site name: "Software Engineering Study Guide"
- Author: Richard
- Deployed to GitHub Pages via GitHub Actions on push to `main`

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

1. **ELI5** — simplified explanation (learner fills in)
2. **Quick Quiz** — pre-implementation predictions (learner fills in)
3. **Implementation** — Java code stub with TODOs
4. **Decision Framework** — when/how to use the concept (learner fills in)
5. **Practice Scenarios** — real-world application questions
6. **Review Checklist** — self-assessment checkboxes

Topics live in two sections:

- `docs/systems/` — 16 topics: storage engines, networking, caching, API design, security, rate limiting, load balancing, concurrency, database scaling, message queues, stream processing, observability, distributed transactions, consensus
- `docs/dsa/` — 17 topics: two pointers, sliding window, hash tables, linked lists, stacks & queues, trees (traversals + recursion), binary search, heaps, graphs, union-find, advanced graphs, backtracking, DP 1D, DP 2D, tries, advanced topics

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
| `.learner-prompt` | Individual list item prompts | Yellow background |
| `.code-reference` | Reference/example code | Gray background |

Reference material (code stubs, algorithm explanations, complexity tables) is left unstyled.

---

## Common Commands

```bash
# Install dependencies
uv sync

# Local dev server (live reload)
uv run mkdocs serve

# Build static site
uv run mkdocs build

# Build with strict mode (fails on warnings)
uv run mkdocs build --strict

# Deploy to GitHub Pages manually
uv run mkdocs gh-deploy
```

---

## Content Authoring Notes

- New topics: create file in `docs/systems/` or `docs/dsa/`, then register in `mkdocs.yml` nav
- `md_in_html` extension is enabled — `markdown` attribute on divs processes inner Markdown
- See `STYLING-GUIDE.md` for full CSS class usage and conversion tips
- See `docs/systems/01-storage-engines.md` as the canonical example of a fully-styled topic

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

## [Offering] Runbook Generation Context
