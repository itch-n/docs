# CSS Styling Guide for Learner Sections

This guide shows how to use the custom CSS classes to distinguish auto-generated content from learner-filled sections.

## Visual Design Principle

- **🟨 Yellow/Orange** = Learner sections (areas to fill in)
- **⬜ Gray/Neutral** = Reference material (auto-generated code and examples)
- **✅ Green** = Completed checklist items

---

## Available CSS Classes

### 1. `.learner-section` - Wrap entire learner sections

Use this to wrap entire sections where the learner fills in content.

**Markdown:**
```markdown
## ELI5: Explain Like I'm 5

<div class="learner-section" markdown>

**Your task:** After implementing, explain the concept.

1. **Question 1:**
   - Your answer: <span class="fill-in">[Fill in]</span>

2. **Question 2:**
   - Your answer: <span class="fill-in">[Fill in]</span>

</div>
```

**Renders as:** Light yellow background with warm left border

---

### 2. `.fill-in` - Individual fill-in prompts

Use this for inline fill-in-the-blank prompts.

**Markdown:**
```markdown

- Your answer: <span class="fill-in">[Fill in after implementation]</span>
- Time complexity: <span class="fill-in">O(?)</span>
- Best choice: <span class="fill-in">[Option A/Option B - Why?]</span>
```

**Renders as:** Bold orange text with light background and border

---

### 3. `.benchmark-table` - Tables for recording results

Use this for tables where learners record benchmark or test results.

**Markdown:**
```markdown
<table class="benchmark-table">
<thead>
  <tr>
    <th>Metric</th>
    <th>Result 1</th>
    <th>Result 2</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td>Write Performance</td>
    <td class="blank">___ ms</td>
    <td class="blank">___ ms</td>
  </tr>
  <tr>
    <td>Read Performance</td>
    <td class="blank">___ ms</td>
    <td class="blank">___ ms</td>
  </tr>
</tbody>
</table>
```

**Renders as:** Yellow-bordered table with light yellow header

---

### 4. `.learner-prompt` - Individual list items with prompts

Use this for list items that contain learner prompts.

**Markdown:**
```markdown
<ul>
  <li class="learner-prompt">
    <strong>Bug 1 location:</strong> <span class="fill-in">[Which lines?]</span>
  </li>
  <li class="learner-prompt">
    <strong>Bug 1 explanation:</strong> <span class="fill-in">[What happens?]</span>
  </li>
</ul>
```

**Renders as:** List item with yellow background and left border

---

### 5. `.code-reference` - Auto-generated code examples

Use this to explicitly mark code blocks as reference material.

**Markdown:**
```html
<div class="code-reference">

```java
// This is reference code for learners to study
public void example() {
    // Implementation details...
}
```

</div>
```

**Renders as:** Gray background to distinguish from learner sections

---

## Complete Example: Updating a Topic Section

**Before (plain markdown):**
```markdown
## Quick Quiz

**Your task:** Test your intuition.

1. **What is the time complexity?**
   - Your guess: _[O(?)]_
   - Verified: _[O(?)]_

2. **When would you use this pattern?**
   - Your answer: _[Fill in]_
```

**After (with CSS classes):**
```markdown
## Quick Quiz

<div class="learner-section" markdown>

**Your task:** Test your intuition.

1. **What is the time complexity?**
   - Your guess: <span class="fill-in">[O(?)]</span>
   - Verified: <span class="fill-in">[O(?)]</span>

2. **When would you use this pattern?**
   - Your answer: <span class="fill-in">[Fill in]</span>

</div>
```

---

## Sections to Apply These Classes

Apply these classes to:

1. **ELI5 sections** - `.learner-section` wrapper
2. **Quick Quiz sections** - `.learner-section` wrapper
3. **Decision Framework sections** - `.learner-section` wrapper
4. **Practice scenario answers** - `.learner-section` wrapper
5. **Benchmark results tables** - `.benchmark-table`
6. **"After implementing, explain" sections** - `.learner-section` wrapper
7. **Debugging challenges answers** - `.learner-prompt` for individual items
8. **Review checklists** - No class needed (already styled automatically)

---

## What NOT to Style

Leave these as-is (no special classes needed):

- Code stubs with `TODO` comments (already gray)
- Client example code (already gray)
- Algorithm explanations (regular text)
- Complexity analysis tables (standard table styling)
- `<details>` solution blocks (already styled)

---

## Quick Conversion Tips

### Find and Replace Pattern

1. **Italic fill-ins to spans:**
   - Find: `_\[Fill in(.*?)\]_`
   - Replace: `<span class="fill-in">[Fill in$1]</span>`

2. **Wrap learner sections:**
   - Add before section: `<div class="learner-section" markdown>`
   - Add after section: `</div>`

3. **Benchmark result blocks:**
   - Convert code block format to HTML table
   - Add `class="benchmark-table"` to `<table>`
   - Add `class="blank"` to cells with `___` or fill-ins

---

## Testing

After adding classes to a file:

1. Run `uv run mkdocs serve`
2. Navigate to the updated page
3. Check that:
   - Learner sections have yellow background
   - Fill-in prompts are bold orange
   - Tables with blanks are highlighted
   - Reference code remains gray

---

## Example File

See `docs/systems/01-storage-engines.md` for a complete example with:

- `.learner-section` wrapping ELI5 and Quiz sections
- `.fill-in` for all fill-in-the-blank prompts
- `.benchmark-table` for results table
