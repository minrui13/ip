# Console UI Test Plan

## Test setup

- Java version: 25
- Compile command: `javac -d out src/main/java/*.java`
- Launch command: `java -cp out Yappa`
- Comparison: exact output, including whitespace and line breaks. Use `{{TIME_OF_DAY}}` only for the application's time-dependent greeting.

## Test cases

Add one section per test case using this format before running the tests.

### TC-01: _short name_

**Aim:** Describe the user-visible behaviour being checked.

**Input:**

```text
command 1
command 2
```

**Expected output:**

```text
Complete console output produced by this input.
```

