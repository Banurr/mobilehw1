package kbtu.kz

import kbtu.kz.model.Course
import kbtu.kz.model.EnrollmentResult
import kbtu.kz.model.Grade
import kbtu.kz.model.Person
import kbtu.kz.model.Student
import kbtu.kz.model.Teacher
import kbtu.kz.model.University
import kbtu.kz.service.GradeService
import kotlinx.coroutines.runBlocking

fun main() {
    val universityName = "KBTU University System"
    val scholarshipThreshold = 85.0
    val isEnrollmentOpen = true
    var failedRequests = 0

    val university = University(universityName)
    println("=== $universityName ===")

    val aigerim = Teacher(1, "Aigerim Sarsenova", "Computer Science")
    val daniyar = Teacher(2, "Daniyar Omarov", "Mathematics")
    university.teachers.addAll(listOf(aigerim, daniyar))

    university.students.addAll(
        listOf(
            Student(101, "Aruzhan Bekova", "Software Engineering"),
            Student(102, "Timur Nurgaliev", "Software Engineering"),
            Student(103, "Madina Kenzhebayeva", "Information Systems"),
            Student(104, "Alikhan Sadykov", "Information Systems")
        )
    )

    university.addCourse(Course("CS101", "Programming Fundamentals", 5), aigerim)
    university.addCourse(Course("CS301", "Mobile Development", 5), aigerim)
    university.addCourse(Course("IS210", "Databases", 4), aigerim)
    university.addCourse(Course("MATH201", "Discrete Mathematics", 4), daniyar)

    println("\n=== People (inheritance and polymorphism) ===")
    val people: List<Person> = university.students + university.teachers
    for (person in people) {
        person.greet()
    }

    println("\n=== Enrollment (sealed class) ===")
    val requests = listOf(
        101 to "CS101", 101 to "CS301", 101 to "MATH201",
        102 to "CS101", 102 to "CS301",
        103 to "IS210", 103 to "CS101",
        104 to "IS210", 104 to "MATH201",
        101 to "CS101",
        999 to "CS101",
        102 to "PHYS100"
    )
    if (isEnrollmentOpen) {
        for ((studentId, courseCode) in requests) {
            val result = university.enroll(studentId, courseCode)
            val message = when (result) {
                is EnrollmentResult.Success -> "OK: ${result.studentName} enrolled in ${result.courseTitle}"
                EnrollmentResult.AlreadyEnrolled -> "Student $studentId is already enrolled in $courseCode"
                EnrollmentResult.StudentNotFound -> "Student $studentId not found"
                EnrollmentResult.CourseNotFound -> "Course $courseCode not found"
            }
            println(message)
            if (result !is EnrollmentResult.Success) {
                failedRequests++
            }
        }
    } else {
        println("Enrollment is closed")
    }
    println("Failed requests: $failedRequests")

    println("\n=== Loading grades (suspend function and coroutines) ===")
    val gradeService = GradeService(
        mapOf(
            101 to listOf(Grade("CS101", 92), Grade("CS301", 88), Grade("MATH201", 95)),
            102 to listOf(Grade("CS101", 45), Grade("CS301", 70)),
            103 to listOf(Grade("IS210", 81), Grade("CS101", 77)),
            104 to listOf(Grade("IS210", 55), Grade("MATH201", 40))
        )
    )
    runBlocking {
        university.loadGrades(gradeService)
    }
    println("Grades loaded for ${university.students.size} students")
    for (student in university.students) {
        val line = student.grades.joinToString { "${it.courseCode}=${it.score}(${it.letter})" }
        println("${student.name}: $line")
    }

    println("\n=== Reports (interface) ===")
    for (item in university.allReports()) {
        println(item.report())
    }

    println("\n=== Collection operations (filter, map, reduce) ===")
    val isTopStudent: (Student) -> Boolean = { it.averageScore() >= scholarshipThreshold }
    val scholars = university.findStudents(isTopStudent)
    println("Scholarship students (average >= $scholarshipThreshold): ${scholars.map { it.name }}")

    val atRisk = university.findStudents { student -> student.grades.any { !it.passed } }
    println("Students with a failed course: ${atRisk.map { it.name }}")

    for (student in university.students) {
        println("${student.name}: total credits = ${university.totalCredits(student)}")
    }

    println("\n=== Ranking ===")
    val ranking = university.students.sortedByDescending { it.averageScore() }
    var place = 1
    while (place <= ranking.size) {
        val student = ranking[place - 1]
        println("$place. ${student.name} - ${student.averageScore()}")
        place++
    }

    println("\n=== Map operations ===")
    for ((major, group) in university.studentsByMajor()) {
        println("$major: ${group.map { it.name }}")
    }
    for ((courseCode, average) in university.courseAverages()) {
        println("Average score in $courseCode: $average")
    }

    println("\n=== Set operations ===")
    val first = university.students[0]
    val second = university.students[1]
    println("${first.name} and ${second.name} share: ${first.courseCodes.intersect(second.courseCodes)}")
    println("Together they take: ${first.courseCodes.union(second.courseCodes)}")
}
