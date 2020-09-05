package com.tom.funfacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class QuizListFragment private constructor() : Fragment() {
    companion object {
        val instance by lazy {
            QuizListFragment()
        }
    }
    lateinit var recycler : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz_list, container, false)
        recycler = view.findViewById<RecyclerView>(R.id.recycler)
        return view
    }
}