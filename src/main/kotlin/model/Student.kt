package kbtu.kz.model

class Student(id: Int, name: String, val major: String) : Person(id, name), Reportable {

    val courseCodes = mutableSetOf<String>()
    val grades = mutableListOf<Grade>()

    override fun greet() {
        println("Hi, i am student with id: $id and my name is: $name , my major is: $major")
    }

    fun averageScore(): Double {
        return if (grades.isEmpty()) 0.0 else grades.map { it.score }.average()
    }

    override fun report(): String {
        val failedCourses = grades.count { !it.passed }
        return "Student #$id $name | $major | courses: ${courseCodes.size} | " +
            "average: ${averageScore()} | failed courses: $failedCourses"
    }
}
