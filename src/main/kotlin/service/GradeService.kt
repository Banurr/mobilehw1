package kbtu.kz.service

import kbtu.kz.model.Grade
import kotlinx.coroutines.delay

class GradeService(private val database: Map<Int, List<Grade>>) {

    suspend fun loadGrades(studentId: Int): List<Grade> {
        delay(300)
        return database[studentId] ?: emptyList()
    }
}