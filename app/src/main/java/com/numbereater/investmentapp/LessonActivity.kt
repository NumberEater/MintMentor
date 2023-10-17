package com.numbereater.investmentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlin.properties.Delegates

class LessonActivity : AppCompatActivity() {
    private var lessonId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lessonId = intent.getIntExtra("lesson-id", -1)
        if (lessonId != -1) {
            // Set layout to passed layoutId from learn fragment
            setContentView(Constants.LESSONS[lessonId])
        } else {
            // Set empty view if no layout is passed
            setContentView(View(applicationContext))
        }

        findViewById<Button>(R.id.finish_button)?.setOnClickListener {
            val database = LessonProgressDatabase(applicationContext)
            if (!database.isComplete(lessonId)) {
                database.setLessonComplete(lessonId)
            }
            database.close()

            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("lesson-complete", true)
            startActivity(intent)
        }
    }
}