package com.tom.funfacts


data class Quiz(val question: String,
                val answers: List<String>,
                val correct: Int)