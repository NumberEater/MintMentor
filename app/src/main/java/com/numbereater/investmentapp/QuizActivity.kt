package com.numbereater.investmentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import com.google.gson.Gson
import java.lang.RuntimeException

class QuizActivity : AppCompatActivity() {

    private lateinit var checkAnswerButton: Button
    private lateinit var nextQuestionButton: Button
    private lateinit var finishQuizButton: Button

    private lateinit var quiz: LessonQuiz

    private var currentQuestionIndex = -1;
    private lateinit var currentQuestionPage: QuestionPage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        quiz = getQuizFromIntent()

        checkAnswerButton = findViewById(R.id.check_answer_button)
        nextQuestionButton = findViewById(R.id.next_question_button)
        finishQuizButton = findViewById(R.id.finish_quiz_button)

        nextQuestion()

        checkAnswerButton.setOnClickListener {
            checkAnswer()
            showRelevantButton()
        }

        nextQuestionButton.setOnClickListener {
            nextQuestion()
        }

        finishQuizButton.setOnClickListener {
            finishQuiz()
        }
    }

    private fun getQuizFromIntent(): LessonQuiz {
        val quizJson = intent.getStringExtra(Constants.QUIZ_JSON_INTENT_TAG)
            ?: throw RuntimeException("No quiz passed to QuizActivity")

        val gson = Gson()
        return gson.fromJson(quizJson, LessonQuiz::class.java)
    }

    private fun nextQuestion() {
        setNextQuestionPage()
        reloadQuestionFragment()

        showCheckAnswerButton()
        hideNextQuestionButton()
    }

    private fun checkAnswer() {
        currentQuestionPage.displayQuestionResults()
    }

    private fun showRelevantButton() {
        if (currentQuestionPage.isQuestionAnswered()) {
            hideCheckAnswerButton()
            if (isLastQuestion()) {
                showFinishQuizButton()
            } else {
                showNextQuestionButton()
            }
        }
    }

    private fun isLastQuestion(): Boolean {
        return (currentQuestionIndex == quiz.questions.size - 1)
    }

    private fun finishQuiz() {
        LessonProgressDatabase(applicationContext).let {
            it.setLessonComplete(quiz.lessonId)
            it.close()
        }

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra(Constants.LESSON_COMPLETE_INTENT_TAG, true)
        startActivity(intent)
    }

    private fun setNextQuestionPage() {
        currentQuestionIndex += 1
        currentQuestionPage = QuestionPage(quiz.questions[currentQuestionIndex])
    }

    private fun reloadQuestionFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.question_frame, currentQuestionPage)
            .commit()
    }

    private fun hideCheckAnswerButton() {
        checkAnswerButton.visibility = View.GONE
    }
    private fun showCheckAnswerButton() {
        checkAnswerButton.visibility = View.VISIBLE
    }

    private fun showNextQuestionButton() {
        nextQuestionButton.visibility = View.VISIBLE
    }
    private fun hideNextQuestionButton() {
        nextQuestionButton.visibility = View.GONE
    }

    private fun showFinishQuizButton() {
        finishQuizButton.visibility = View.VISIBLE
    }
}