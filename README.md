# kio #

Kio is a simple Kotlin IO library that targets to simplify File IO operations.

## Features ##

- Extension properties for accessing file content.
- Assertion methods, e.g. `File.requireExists()` and `File.requireNotExists()`
- Creates file if the file doesn't exist (or delete if exists).
- Do nothing if the file doesn't exist (or already exists in some case).