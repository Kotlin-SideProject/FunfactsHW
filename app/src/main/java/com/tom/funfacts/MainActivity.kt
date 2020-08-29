package com.tom.funfacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        readQuestions()
        val db = Room.databaseBuilder(
            this,
            QuizDatabase::class.java,
            "quiz.db"
        ).build()
        db.quizDao().add(
            Quiz(1,
                "aa",
                "xxyyzz",
//                listOf("xx","yy","zz"),
                1))
    }

    private fun readQuestions() {
//        Log.d(TAG, "readQuestions: ${}")
        val data = resources.openRawResource(R.raw.questions)
                .bufferedReader()
                .readText()
        val questions = JSONArray(data)
//        val bag = listOf<Question>()
        val bag = mutableListOf<Quiz>()
        for (i in 0..questions.length()-1) {
            val obj = questions.getJSONObject(i)
            val q = obj.getString("question")
            val correct = obj.getInt("correct")
            val answers = obj.getJSONArray("answers")
            val baggg = mutableListOf<String>()
            for (j in 0..answers.length()-1) {
                Log.d(TAG, "Answer: ${answers.getString(j)}");
                baggg.add(answers.getString(j))
            }
            bag.add(Quiz(1, q, "xxyyzz", correct))
            Log.d(TAG, "question: $q $correct $answers");
        }

    }
}
