package com.tom.funfacts

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Quiz::class, Answer::class), version = 1, exportSchema = false)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun quizDao() : QuizDao
    companion object {
        private var instance : QuizDatabase? = null
        fun getInstance(context: Context) :  QuizDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context,
                    QuizDatabase::class.java,
                    "quiz.db")
                    .build()
            }
            return instance!!
        }
    }



}