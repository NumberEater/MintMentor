package com.numbereater.investmentapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class LearnFragment : Fragment() {

    private fun launchLesson(lessonId: Int) {
        val intent = Intent(context, LessonActivity::class.java)
        intent.putExtra("lesson-id", lessonId)
        startActivity(intent)
    }

//    private fun createLessonButton(
//        textResourceId: Int,
//        isCompleteStyle: Boolean,
//        layout: ViewGroup
//    ) {
//
//        var localContext = context
//
//        if (isCompleteStyle) {
//            localContext = ContextThemeWrapper(context, Constants.BUTTON_COMPLETE_STYLE)
//        }
//
//        val button = Button(localContext)
//        button.setText(textResourceId)
//
//        val layoutParams = LayoutParams()
//
//
//    }
    
    private fun configureButtons(layout: View) {
        val lessonButtons = arrayOf(
            layout.findViewById<Button>(R.id.investment_basics_button),
            layout.findViewById<Button>(R.id.financial_goals_button),
            layout.findViewById<Button>(R.id.investment_securities_button),
            layout.findViewById<Button>(R.id.risk_return_button),
            layout.findViewById<Button>(R.id.portfolio_construction_button),
            layout.findViewById<Button>(R.id.investment_strategies_button),
            layout.findViewById<Button>(R.id.tax_considerations_button),
            layout.findViewById<Button>(R.id.investing_platforms_button)
        )

        val database = LessonProgressDatabase(requireContext())
        for (i in lessonButtons.indices) {
            lessonButtons[i].setOnClickListener { launchLesson(i) }
            if (database.isComplete(i)) {
                lessonButtons[i].setBackgroundColor(Color.DKGRAY)
            }
        }
        database.close()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflatedView = inflater.inflate(R.layout.fragment_learn, container, false)

        configureButtons(inflatedView)

        return inflatedView;
    }
}