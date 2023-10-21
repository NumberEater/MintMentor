package com.numbereater.investmentapp

data class QuizQuestion(
    private val question: String,
    private val choices: Array<String>,
    private val correctAnswerIndex: Int)