package kbtu.kz.model

sealed class EnrollmentResult {
    data class Success(val studentName: String, val courseTitle: String) : EnrollmentResult()
    object AlreadyEnrolled : EnrollmentResult()
    object StudentNotFound : EnrollmentResult()
    object CourseNotFound : EnrollmentResult()
}
