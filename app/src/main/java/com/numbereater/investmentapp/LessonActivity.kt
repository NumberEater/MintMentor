package com.numbereater.investmentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlin.properties.Delegates

class LessonActivity : AppCompatActivity() {
    private var lessonId = -1

    private fun finishLessonButtonAction() {
        if (!isLessonComplete()) {
            setLessonComplete()
        }
        returnToLearnFragment()
    }

    private fun isLessonComplete(): Boolean {
        val database = LessonProgressDatabase(applicationContext)
        val isComplete = database.isComplete(lessonId)
        database.close()
        return isComplete
    }

    private fun setLessonComplete() {
        val database = LessonProgressDatabase(applicationContext)
        database.setLessonComplete(lessonId)
        database.close()
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

        findViewById<Button>(R.id.finish_button).setOnClickListener {
            finishLessonButtonAction()
        }
    }
}