package com.tom.funfacts

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Quiz::class), version = 4)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun quizDao() : QuizDao
}