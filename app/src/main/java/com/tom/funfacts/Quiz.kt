package com.tom.funfacts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "quizs")
data class Quiz(
    @ColumnInfo(name = "qq")val question: String,
    val correct: Int) {
    @PrimaryKey var id: Long? = null
    @Ignore
    val answers: List<Answer> = listOf<Answer>()
}

@Entity
class Answer(val quizId: Long, val text: String) {
    @PrimaryKey
    var id: Long? = null
}
