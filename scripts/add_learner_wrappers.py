#!/usr/bin/env python3
"""
Add <div class="learner-section" markdown> wrappers to Decision Framework
and Practice sections across all docs files.
"""
import re
import glob
from pathlib import Path


def wrap_section(content, start_pat, end_pats):
    """
    Wrap a markdown section in learner-section div.

    Finds the section starting with start_pat, ends before the first end_pats match.
    Inserts <div> after the heading and </div> before the --- separator
    that precedes the next section heading.

    Returns (modified_content, was_changed).
    """
    lines = content.split('\n')

    # Find the section heading
    start_idx = None
    for i, line in enumerate(lines):
        if re.match(start_pat, line):
            start_idx = i
            break

    if start_idx is None:
        return content, False

    # Skip if already wrapped (check next 5 lines for existing div)
    for i in range(start_idx + 1, min(start_idx + 6, len(lines))):
        if 'class="learner-section"' in lines[i]:
            return content, False

    # Find end heading index
    end_idx = len(lines)
    for i in range(start_idx + 1, len(lines)):
        for pat in end_pats:
            if re.match(pat, lines[i]):
                end_idx = i
                break
        if end_idx < len(lines):
            break

    # Find close_before: walk back from end_idx to find the --- separator
    # that immediately precedes the next section heading.
    close_before = end_idx
    for j in range(end_idx - 1, start_idx, -1):
        stripped = lines[j].strip()
        if stripped == '---':
            close_before = j
            break
        elif stripped:
            # Last non-blank line before end; insert </div> after it
            close_before = j + 1
            break

    # Find first non-blank line after heading (where content begins)
    content_start = start_idx + 1
    while content_start < end_idx and not lines[content_start].strip():
        content_start += 1

    # Get content lines, strip trailing blanks
    section_content = lines[content_start:close_before]
    while section_content and not section_content[-1].strip():
        section_content.pop()

    # Assemble new lines
    new_lines = (
        lines[:start_idx + 1]                           # heading
        + ['', '<div class="learner-section" markdown>', '']  # open wrapper
        + section_content                               # content
        + ['', '</div>', '']                            # close wrapper
        + lines[close_before:]                          # --- and next section
    )

    new_content = '\n'.join(new_lines)
    return new_content, new_content != content


def process_file(filepath):
    path = Path(filepath)
    original = path.read_text()
    content = original

    # 1. Wrap Decision Framework (ends at Practice or Practice Scenarios)
    content, _ = wrap_section(
        content,
        r'^## Decision Framework$',
        [r'^## Practice( Scenarios)?$', r'^## Practice$']
    )

    # 2. Wrap Practice / Practice Scenarios (ends at Test Your Understanding)
    content, _ = wrap_section(
        content,
        r'^## Practice( Scenarios)?$',
        [r'^## Test Your Understanding$']
    )

    if content != original:
        path.write_text(content)
        return True
    return False


if __name__ == '__main__':
    files = sorted(
        glob.glob('docs/systems/*.md') +
        glob.glob('docs/dsa/*.md')
    )

    updated = 0
    for filepath in files:
        changed = process_file(filepath)
        status = 'UPDATED' if changed else 'no change'
        print(f"  {status}: {Path(filepath).name}")
        if changed:
            updated += 1

    print(f"\n{updated}/{len(files)} files updated.")
