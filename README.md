# University System (Kotlin Console App)

A small Kotlin/JVM console application that simulates a university. It creates teachers, students and courses, enrolls students, loads their grades asynchronously with coroutines, and prints reports and statistics. It is a plain Kotlin project, not an Android application.

## How to run

Requirements: JDK 17 and IntelliJ IDEA (or any terminal).

Terminal (from the project folder):

```
./gradlew run
```

## Where the requirements are demonstrated

| Requirement                                | File                                                                        | Where                                                                                                                                                        |
|--------------------------------------------|-----------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Variables, data types, conditions, loops   | `Main.kt`                                                                   | `val`/`var` with String, Double, Boolean, Int; `if/else`; `when`; `for` and `while` loops                                                                    |
| List                                       | `University.kt`, `Main.kt`                                                  | `students`, `teachers`, `requests`                                                                                                                           |
| Set                                        | `Student.kt`, `Main.kt`                                                     | `courseCodes` is a `MutableSet`; `intersect` and `union` in the "Set operations" section                                                                     |
| Map                                        | `University.kt`, `GradeService.kt`                                          | `courses` map; `studentsByMajor()`, `courseAverages()`; grades "database"                                                                                    |
| map, filter, reduce                        | `University.kt`, `Main.kt`                                                  | `map` (names, scores), `filter` in `findStudents`, `reduce` in `totalCredits()`; also `groupBy`, `flatMap`, `sortedByDescending`, `count`, `any`             |
| Functions, higher-order functions, lambdas | `University.kt`, `Main.kt`                                                  | `findStudents(condition: (Student) -> Boolean)` receives lambdas such as `isTopStudent`; extension function `round1()` in `Extensions.kt`                    |
| Classes and objects                        | `University.kt`, `Student.kt`, `Teacher.kt`, `Course.kt`, `GradeService.kt` | `University`, `Student`, `Teacher`, `GradeService` objects created in `main()`                                                                               |
| Inheritance                                | `Person.kt`, `Student.kt`, `Teacher.kt`                                     | `Student` and `Teacher` extend `Person` and override `greet()`                                                                                               |
| Interface and polymorphism                 | `Reportable.kt`, `Student.kt`, `Teacher.kt`                                 | `Reportable.report()` is implemented by both classes; `allReports()` returns a `List<Reportable>` and `greet()` is called through `List<Person>`             |
| Data class                                 | `Course.kt`, `Grade.kt`                                                     | `Course` and `Grade`                                                                                                                                         |
| Sealed class                               | `EnrollmentResult.kt`                                                       | `enroll()` returns `Success`, `AlreadyEnrolled`, `StudentNotFound` or `CourseNotFound`; `Main.kt` handles them with an exhaustive `when`                     |
| Suspend function and coroutine             | `GradeService.kt`, `University.kt`, `Main.kt`                               | `suspend fun loadGrades()` uses `delay`; `University.loadGrades()` starts one `async` per student inside `coroutineScope`; `runBlocking` in `main()` runs it |