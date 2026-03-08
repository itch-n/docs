# GEMINI.md

Project context for Gemini CLI sessions. For full conventions see `AGENTS.md` (and `AGENTS.local.md` for your local setup).

---

## Project

**itch-n-docs** — MkDocs static site (backend engineering study guide). Java 25 implementations, Python 3.13 via `uv`.

- Docs: `docs/systems/` (20 topics) and `docs/dsa/` (15 topics)
- Java: `src/main/java/com/study/` — package per topic (e.g. `com.study.systems.ratelimiting`)
- Tests: `src/test/java/com/study/` — JUnit 5, intentionally fail until TODOs are implemented

## Critical Rules

**Admonitions** — Cinder theme only supports three types. Never use `!!! info`, `!!! tip`, `!!! success`:

- `!!! note` — blue
- `!!! warning` — yellow/orange
- `!!! danger` — red

**Blank line before lists** — Python-Markdown requires it. Missing blank line is a silent failure (renders as plain text, not bullets):

```markdown
# Wrong
Your task:
- Item 1

# Correct
Your task:

- Item 1
```

**`??? success`** — collapsible blocks use `success` type (rendered correctly by pymdownx.details even though Cinder doesn't style `!!! success`).

**`bs-callout-info` div** — "Where this topic connects" uses a custom div, not an admonition:

```markdown
<div class="bs-callout bs-callout-info" markdown>

**Where this topic connects**

- **Topic** — explanation → [Link](file.md)

</div>
```

## Commands

```bash
uv run mkdocs serve --dirtyreload   # local dev
./gradlew test                       # run Java tests
./gradlew test --tests "com.study.dsa.binarysearch.*"  # single package
```

## Local Config

See `AGENTS.local.md` for git workflow, remote layout, and personal branch configuration.
