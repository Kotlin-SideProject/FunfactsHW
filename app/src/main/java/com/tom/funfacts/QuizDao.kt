package com.tom.funfacts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(quiz: Quiz) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnswers(answers: List<Answer>) : Array<Long>

    @Query("select * from quizs")
    fun getAll() : List<Quiz>
}