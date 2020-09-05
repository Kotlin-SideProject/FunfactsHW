package com.tom.funfacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.room.Room
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        readQuestions()
        /*val db = Room.databaseBuilder(
            this,
            QuizDatabase::class.java,
            "quiz.db"
        ).build()*/
        val db = QuizDatabase.getInstance(this)
        Thread(
            Runnable {
                val id = db?.quizDao()?.add(
                    Quiz(
                        "aa",
                        1)
                    )
                Log.d(TAG, "Quiz id: $id");
                //test answers
                id?.also {quizId ->
                    val answers = listOf<Answer>(
                        Answer(quizId, "xxxxx"),
                        Answer(quizId, "yyyy"),
                        Answer(quizId, "zzzz")
                    )
                    val ids = db?.quizDao()?.insertAnswers(answers)
                    Log.d(TAG, "ids: $ids");
                }

            }
        ).start()
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
            bag.add(Quiz( q, correct))
            Log.d(TAG, "question: $q $correct $answers");
        }

    }
}
