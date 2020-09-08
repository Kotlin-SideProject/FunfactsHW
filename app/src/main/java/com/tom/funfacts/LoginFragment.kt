package com.tom.funfacts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment private constructor(): Fragment() {
    companion object {
        val instance by lazy {
            LoginFragment()
        }
        val TAG = LoginFragment.javaClass.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onResume() {
        super.onResume()
        login.setOnClickListener {
            if (password.text.toString() == "1234"){
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.container, QuizListFragment.instance)
                    commit()
                }
            }
        }
    }
}