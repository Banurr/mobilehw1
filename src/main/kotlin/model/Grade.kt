package kbtu.kz.model

const val PASSING_SCORE = 50

data class Grade(val courseCode: String, val score: Int) {

    val passed: Boolean
        get() = score >= PASSING_SCORE

    val letter: Char
        get() = when {
            score >= 90 -> 'A'
            score >= 75 -> 'B'
            score >= 60 -> 'C'
            score >= PASSING_SCORE -> 'D'
            else -> 'F'
        }
}
