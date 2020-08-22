package com.tom.funfacts

data class Question(val title: String,
                    val answers: List<String>,
                    val correct: Int)