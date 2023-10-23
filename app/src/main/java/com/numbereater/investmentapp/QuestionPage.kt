package com.numbereater.investmentapp

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class QuestionPage(private val question: QuizQuestion) : Fragment() {

    private var selectedAnswer = -1

    private lateinit var layout: LinearLayout
    private lateinit var questionButtons: Array<RadioButton>
    private lateinit var questionText: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_question_template, container, false) as LinearLayout

        findQuestionText()
        setQuestionText()

        findQuestionButtons()
        setButtonTextValues()
        setAnswerButtonGroupActions()

        return layout
    }

    private fun findQuestionText() {
        questionText = layout.findViewById<TextView>(R.id.question_text)
    }

    private fun setQuestionText() {
        questionText.setText(question.questionTextResource)
    }

    private fun findQuestionButtons() {
        questionButtons = arrayOf(
            layout.findViewById(R.id.answer_button_1),
            layout.findViewById(R.id.answer_button_2),
            layout.findViewById(R.id.answer_button_3),
            layout.findViewById(R.id.answer_button_4),
            layout.findViewById(R.id.answer_button_5),
        )
    }

    private fun setButtonTextValues() {
        for (i in questionButtons.indices) {
            questionButtons[i].setText(question.choiceTextResources[i])
        }
    }

    private fun setAnswerButtonGroupActions() {
        val answerButtonGroup = layout.findViewById<RadioGroup>(R.id.answer_button_group)
        answerButtonGroup.setOnCheckedChangeListener { _: RadioGroup, button: Int ->
            when (button) {
                R.id.answer_button_1 -> selectedAnswer = 0
                R.id.answer_button_2 -> selectedAnswer = 1
                R.id.answer_button_3 -> selectedAnswer = 2
                R.id.answer_button_4 -> selectedAnswer = 3
                R.id.answer_button_5 -> selectedAnswer = 4
            }
        }
    }

    fun isQuestionAnswered(): Boolean {
        return (selectedAnswer != -1)
    }

    fun displayQuestionResults() {
        if (selectedAnswer == -1) {
            showSelectAnswerMessage()
        } else {
            highlightCorrectAnswer()

            if (!isCorrectAnswerSelected()) {
                highlightWrongAnswer()
            }
        }
    }

    private fun showSelectAnswerMessage() {
        Toast.makeText(context, R.string.please_select_answer, Toast.LENGTH_SHORT).show()
    }

    private fun highlightCorrectAnswer() {
        questionButtons[question.correctAnswerIndex].setBackgroundColor(
            resources.getColor(R.color.correct_answer, requireContext().theme)
        )
    }

    private fun highlightWrongAnswer() {
        questionButtons[selectedAnswer].setBackgroundColor(
            resources.getColor(R.color.wrong_answer, requireContext().theme)
        )
    }

    private fun isCorrectAnswerSelected(): Boolean {
        return (selectedAnswer == question.correctAnswerIndex)
    }
}