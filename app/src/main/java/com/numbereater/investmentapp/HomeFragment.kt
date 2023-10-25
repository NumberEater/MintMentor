package com.numbereater.investmentapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import org.w3c.dom.Text
import kotlin.math.ceil

class HomeFragment : BottomNavigationFragment() {
    override val bottomNavigationButtonId = R.id.home_action

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_home, container, false)


        val lessonProgressBar = layout.findViewById<ProgressBar>(R.id.lesson_completion_bar)
        val lessonsCompletedLabel = layout.findViewById<TextView>(R.id.lesson_count_label)

        val database = LessonProgressDatabase(requireContext())
        val lessonsCompleted = database.getLessonsCompletedCount()
        database.close()

        lessonProgressBar.progress = ceil((100.0 / 7.0) * lessonsCompleted).toInt()

        lessonsCompletedLabel.text = String.format(
            "You've completed %d/7 lessons",
            lessonsCompleted
        )

        val completeDatabaseButton = layout.findViewById<Button>(R.id.complete_database_button)
        completeDatabaseButton.setOnClickListener {
            completeDatabase()
        }
        val clearDatabaseButton = layout.findViewById<Button>(R.id.clear_database_button)
        clearDatabaseButton.setOnClickListener {
            clearDatabase()
        }

        return layout
    }

    private fun completeDatabase() {
        val database = LessonProgressDatabase(requireContext())
        for (i in 0..6) {
            database.setLessonComplete(i)
        }
        database.close()
    }

    private fun clearDatabase() {
        val database = LessonProgressDatabase(requireContext())
        database.removeAllEntries()
        database.close()
    }
}