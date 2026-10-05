package kbtu.kz.model

import kbtu.kz.service.GradeService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class University(val name: String) {

    val students = mutableListOf<Student>()
    val teachers = mutableListOf<Teacher>()
    val courses = mutableMapOf<String, Course>()

    fun addCourse(course: Course, teacher: Teacher) {
        courses[course.code] = course
        teacher.courseCodes.add(course.code)
    }

    fun enroll(studentId: Int, courseCode: String): EnrollmentResult {
        val student = students.find { it.id == studentId } ?: return EnrollmentResult.StudentNotFound
        val course = courses[courseCode] ?: return EnrollmentResult.CourseNotFound
        return if (student.courseCodes.add(course.code)) {
            EnrollmentResult.Success(student.name, course.title)
        } else {
            EnrollmentResult.AlreadyEnrolled
        }
    }

    fun findStudents(condition: (Student) -> Boolean): List<Student> = students.filter(condition)

    fun allReports(): List<Reportable> = students + teachers

    fun studentsByMajor(): Map<String, List<Student>> = students.groupBy { it.major }

    fun courseAverages(): Map<String, Double> =
        students.flatMap { it.grades }
            .groupBy { it.courseCode }
            .mapValues { entry -> entry.value.map { it.score }.average() }

    fun totalCredits(student: Student): Int {
        val credits = student.courseCodes.mapNotNull { courses[it]?.credits }
        return if (credits.isEmpty()) 0 else credits.reduce { sum, credit -> sum + credit }
    }

    suspend fun loadGrades(service: GradeService) = coroutineScope {
        val loaded = students.map { student ->
            async { student to service.loadGrades(student.id) }
        }.awaitAll()
        for ((student, grades) in loaded) {
            student.grades.addAll(grades)
        }
    }
}