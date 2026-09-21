# Assignment #3 — Design Discussion

**Author:** David Ifeanyi

All functional requirements from Assignment #2 are preserved. Assignment #3 produces the
same output file and the same console output; the change is entirely one of design.

---

## How Assignment #2 was organized

Assignment #2 was a single class, `ETLPipeline`, containing only `static` members: `main`,
a `transformRow` method, and a `determinePayLevel` helper. It was a procedural program
written in Java rather than an object-oriented one. There were no objects at all — an
employee existed only as a `String[]` of split fields and a handful of local variables,
and was gone again by the end of the method.

The specific weaknesses were:

- **One method did five jobs.** `transformRow` split the line, validated the fields,
  computed the pay, classified the result, and formatted the output row.
- **No employee abstraction.** The central concept of the program was never named.
- **Business rules mixed with file handling.** The overtime threshold, the overtime
  multiplier, the departmental bonus, and the pay bands sat as constants next to the
  input and output paths.
- **Reading, transforming, and writing were interleaved** inside one `try` block, so no
  stage could be examined or replaced on its own.
- **An invalid row was signalled by returning `null`,** a contract that says nothing
  about why a row was rejected and is easy for a caller to mishandle.

## What design changes I made

I separated the program into objects along two axes: the **stages** of the pipeline
(extract, transform, load) and the **things** the pipeline operates on (an employee, and
that employee's computed payroll figures). Each class now has state it owns and behavior
that acts on that state, and `main` no longer does any work itself.

## Classes introduced, and why

| Class | Responsibility |
| --- | --- |
| `ETLPipeline` | Entry point. Builds the stages, runs them in order, prints the summary. Contains no reading, arithmetic, or formatting. |
| `Employee` | An immutable employee record as read from the input. `private final` fields, getters, no setters. |
| `PayrollRecord` | An employee together with their computed gross pay, pay level, and employment status — the result of the transform stage. |
| `EmployeeCsvParser` | Turns one input line into an `Employee`, or reports it as unusable. Owns every rejection rule. |
| `EmployeeCsvExtractor` | The extract stage. Owns the input file and the read loop. |
| `PayrollTransformer` | The transform stage. Owns the pay policy: overtime threshold and multiplier, departmental bonus, and final rounding. |
| `PayrollCsvWriter` | The load stage. Owns the output file, the header, the column order, and how each value is rendered as text. |
| `PayLevel` (enum) | The four pay bands, each carrying its output label, plus the boundaries between them. |
| `EmploymentStatus` (enum) | Full-time and part-time, plus the hours threshold that distinguishes them. |
| `ETLReport` | The three run counts and the summary printing. |

The two enums replaced an `if`/`else if` chain and a ternary expression. A pay band is a
fixed, known set of values, which is exactly what an enum is for, and putting the
boundaries inside `PayLevel` means the rule for what counts as "High" lives with the
concept of "High" rather than in the middle of a calculation.

I also replaced the `null` return with `Optional<Employee>`. The method signature now
states that a row may legitimately produce no employee, instead of leaving the caller to
discover it.

## How responsibilities were divided differently

The clearest way to describe the change is by what each class is now *unable* to do:

- `Employee` and `PayrollRecord` know nothing about CSV, files, or payroll rules. They are
  plain data with no dependencies.
- `EmployeeCsvParser` knows the shape of an input row but never opens a file, so the
  rejection rules can be exercised without any input on disk.
- `EmployeeCsvExtractor` knows about files but makes no decision about a line's contents;
  it delegates each one to the parser.
- `PayrollTransformer` performs no input or output. It is the only class that knows the
  pay rules, so changing a multiplier or a threshold is a change to one class.
- `PayrollCsvWriter` is the only class that knows the destination is a CSV file. Nothing
  upstream is aware of the output format.
- `ETLReport` holds the counts privately and raises them through named methods, so each
  stage reports the events it observes and no stage can reach into the totals.

Dependencies all point in one direction, from `ETLPipeline` down through the stages to the
data types, with no cycles.

## Why this is an improvement

- **A change has one place to go.** A new pay band is a change to `PayLevel`; a different
  bonus rule is a change to `PayrollTransformer`; a different output format is a change to
  `PayrollCsvWriter`. In Assignment #2 all of these lived in the same method.
- **The program is testable in pieces.** `EmployeeCsvParser`, `PayrollTransformer`, and
  both enums are pure logic with no file access, so they can be tested directly.
- **Each stage is replaceable.** Reading from a database instead of a file means writing
  one new class; nothing downstream would change.
- **State is protected.** Both data classes are immutable, so a record cannot be modified
  after it is created and no partially built object is ever passed around.
- **The program reads as what it is.** `main` now shows the pipeline in three lines —
  extract, transform, write — instead of hiding the sequence inside one loop.

I deliberately did not create some classes that might be expected, because they would have
added indirection without adding meaning:

- **No `PayrollRule` interface with polymorphic rule objects.** There are only two rules,
  and the order in which they apply is significant — overtime must be applied before the
  departmental bonus. An unordered collection of rule objects would hide that constraint.
  This is the natural extension point if the rules multiply.
- **No `Department` class or enum.** A department is a plain label with one special case,
  and an enum would have to cope with arbitrary unlisted departments appearing in the
  input.
- **No separate validator.** Validation is inseparable from parsing here: a field is found
  to be invalid by attempting to parse it.
- **No abstract base classes or single-implementation interfaces for the stages.** There is
  one source and one destination; abstracting over a single case would be speculative.

## Preservation of Assignment #2 behavior

Two details were preserved deliberately, because a natural-looking refactor would have
broken them:

1. **Hours and rate are stored as `double`, not `BigDecimal`.** Assignment #2 parses them
   as `double`, converts to `BigDecimal` only for the pay arithmetic, and formats the
   original `double` for output. Storing a `BigDecimal` instead would change the printed
   value for inputs such as `2.675`, where the nearest `double` is slightly below the
   rounding boundary and `%.2f` therefore yields `2.67`, while `BigDecimal.valueOf(2.675)`
   yields `2.68`.
2. **Gross pay is computed from the unrounded rate** and rounded only once, at the end, so
   a rate of `16.666` is displayed as `16.67` while the pay is computed from `16.666`.

One consequence of separating the stages is worth noting: Assignment #2 opened the input
and output together and wrote the header before reading anything, so a read failure partway
through left a partially written output file. In Assignment #3 extraction completes before
the writer opens, so a read failure leaves the previous output file untouched. The console
output is identical in either case, including the error message.

I verified equivalence by running both versions and comparing results. Against the supplied
`data/employees.csv`, the output file is byte-identical and the console output matches
(14 rows read, 7 transformed, 7 skipped). I also compared both versions against an
18-row file of edge cases — rounding boundaries, a zero-hour row, exactly 30 and exactly 40
hours, a lowercase and a whitespace-padded department, a leading-zero ID, scientific
notation, a negative rate, a blank line, and wrong field counts — and against a missing
input file, a header-only file, and an empty file. All produced identical output.

## AI and Internet resources

**AI tools used:** Yes. I used Gemeni to help with this assignment.
interaction covered breaking down the assignment requirements, identifying the behavior.


Transcript: **[PASTE AI TRANSCRIPT LINK HERE — REQUIRED BEFORE SUBMITTING]**

**Internet resources used:** None beyond the standard Java API documentation.

I am responsible for understanding, testing, and explaining the code and the design
decisions submitted here.
