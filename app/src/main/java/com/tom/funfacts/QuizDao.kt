package com.tom.funfacts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(quiz: Quiz)

    @Query("select * from Quiz")
    fun getAll() : List<Quiz>
}