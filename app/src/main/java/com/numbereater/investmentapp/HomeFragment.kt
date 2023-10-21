package com.numbereater.investmentapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class HomeFragment : BottomNavigationFragment() {
    override val bottomNavigationButtonId = R.id.home_action

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_home, container, false)

        layout.findViewById<Button>(R.id.clear_database_button).setOnClickListener {
            clearDatabaseButtonAction()
        }

        return layout
    }

    private fun clearDatabaseButtonAction() {
        LessonProgressDatabase(requireContext()).let {
            it.clearDatabase()
            it.close()
        }
    }
}