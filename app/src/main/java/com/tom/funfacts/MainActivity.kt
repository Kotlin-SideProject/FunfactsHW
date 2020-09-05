package com.tom.funfacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_quiz_list.*
import kotlinx.android.synthetic.main.row_quiz.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MainActivity.QuizAdapter
    val TAG = MainActivity::class.java.simpleName
    lateinit var quizs : List<Quiz>
    var logon = true
    lateinit var currentFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ////未登入時，顯示 LoginFragment，待設計
        if (!logon) {

        } else {
            //replace Fragment
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container, QuizListFragment.instance)
                commit()
                currentFragment = QuizListFragment.instance
            }
            CoroutineScope(Dispatchers.IO).launch {
                val json = URL("http://dummy.restapiexample.com/api/v1/employees")
                    .readText()
                Log.d(TAG, ":$json ");
            }
            CoroutineScope(Dispatchers.IO).launch {
                quizs = QuizDatabase.getInstance(this@MainActivity)
                    .quizDao().getAll()
                quizs.forEach {
                    Log.d(TAG, ":${it.question} ");
                }
                adapter = QuizAdapter()
                withContext(Dispatchers.Main) {
                    val recycler = (currentFragment as QuizListFragment).recycler
                    recycler.setHasFixedSize(true)
                    recycler.layoutManager = LinearLayoutManager(this@MainActivity)
                    recycler.adapter = adapter
                }
            }
        }
//        readQuestions()
        /*val db = Room.databaseBuilder(
            this,
            QuizDatabase::class.java,
            "quiz.db"
        ).build()*/
//        addTest()
    }

    private fun addTest() {
        /*
        val db = QuizDatabase.getInstance(this)
        Thread(
            Runnable {
                val id = db?.quizDao()?.add(
                    Quiz(
                        "aa",
                        1
                    )
                )
                Log.d(TAG, "Quiz id: $id");
                //test answers
                id?.also { quizId ->
                    val answers = listOf<Answer>(
                        Answer(quizId, "xxxxx"),
                        Answer(quizId, "yyyy"),
                        Answer(quizId, "zzzz")
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        val ids = db?.quizDao()?.insertAnswers(answers)
                        Log.d(TAG, "ids: $ids");
                    }

                }

            }
        ).start()
        */
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
            val baggg = mutableListOf<Answer>()
            for (j in 0..answers.length()-1) {
                Log.d(TAG, "Answer: ${answers.getString(j)}");
                baggg.add(Answer(text = answers.getString(j)))
            }
            val quiz = Quiz( q, correct)
            bag.add(quiz)
            Log.d(TAG, "question: $q $correct $answers");
//            Dispatchers.
            CoroutineScope(Dispatchers.IO).launch {
                QuizDatabase.getInstance(this@MainActivity)
                    .quizDao().apply {
                    val quizId = add(quiz)
                    baggg.forEach {
                        it.quizId = quizId
                    }
                    val ids = insertAnswers(baggg)
//                    withContext()
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@MainActivity, "TEsting", Toast.LENGTH_LONG)
                            .show()
                    }

                }
            }
            /*thread {
                QuizDatabase.getInstance(this).quizDao().apply {
                    val quizId = add(quiz)
                    baggg.forEach {
                        it.quizId = quizId
                    }
                    val ids = insertAnswers(baggg)
                }
            }*/
        }

    }
    class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question = itemView.quiz_question
    }

    inner class QuizAdapter : RecyclerView.Adapter<QuizViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_quiz, parent, false)
//            val v2 = layoutInflater.inflate(R.layout.row_quiz, parent, false)
            return QuizViewHolder(view)
        }

        override fun getItemCount(): Int {
            return quizs.size
        }

        override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
            val quiz = quizs.get(position)
            holder.question.setText(quiz.question)
            holder.itemView.setOnClickListener {
                Log.d(TAG, "Quiz Id: ${quiz.id}");
                Intent(this@MainActivity, QuizActivity::class.java)
                    .apply {
                        putExtra("QUIZ_ID", quiz.id)
                        startActivity(this)
                    }
            }
        }

    }

//    override val coroutineContext: CoroutineContext
//        get() = CoroutineContextou
}
