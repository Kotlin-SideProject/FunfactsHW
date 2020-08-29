package com.tom.funfacts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quiz(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "qq")val question: String,
    val answers: String,
//    val answers: List<String>,
    val correct: Int) {

}