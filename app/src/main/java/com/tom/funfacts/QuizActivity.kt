package com.tom.funfacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizActivity : AppCompatActivity() {
    companion object {
        val TAG = QuizActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val quizId = intent.getLongExtra("QUIZ_ID", -1)
        CoroutineScope(Dispatchers.IO).launch {
            val answers = QuizDatabase.getInstance(this@QuizActivity)
                .quizDao()
                .getAnswersById(quizId)
            answers.forEach {
                Log.d(TAG, ": ${it.text}");
            }
        }
    }
}