package kbtu.kz.model

class Teacher(id: Int, name: String, val department: String) : Person(id, name), Reportable {

    val courseCodes = mutableSetOf<String>()

    override fun greet() {
        println("Hi, i am teacher with id: $id and my name is: $name, working in department: $department")
    }

    override fun report(): String {
        val taught = if (courseCodes.isEmpty()) "no courses" else courseCodes.joinToString(", ")
        return "Teacher #$id $name | $department | teaches: $taught"
    }
}
