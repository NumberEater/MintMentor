package com.numbereater.investmentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.gson.Gson

class LessonActivity : AppCompatActivity() {
    private var lessonId = -1

    private fun takeQuizButtonAction() {
        val gson = Gson()
        val intent = Intent(applicationContext, QuizActivity::class.java)
        intent.putExtra(
            Constants.QUIZ_JSON_INTENT_TAG,
            gson.toJson(Constants.QUIZZES[lessonId], LessonQuiz::class.java)
        )
        startActivity(intent)
    }

    private fun returnToLearnFragment() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra(Constants.LESSON_COMPLETE_INTENT_TAG, true)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lessonId = intent.getIntExtra(Constants.LESSON_ID_INTENT_TAG, -1)
        if (lessonId != -1) {
            setContentView(Constants.LESSONS[lessonId])
        } else {
            returnToLearnFragment()
        }

        findViewById<Button>(R.id.take_quiz_button).setOnClickListener {
            takeQuizButtonAction()
        }

        findViewById<Button>(R.id.return_to_learn_fragment).setOnClickListener {
            returnToLearnFragment()
        }
    }
}