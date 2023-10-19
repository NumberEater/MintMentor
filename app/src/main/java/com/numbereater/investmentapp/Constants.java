package com.numbereater.investmentapp;

public class Constants {
    /* An array of layouts where the index of each
    layout corresponds to the lesson number */
    public static final int[] LESSONS = {
            R.layout.lesson_investment_basics,
            R.layout.lesson_setting_financial_goals,
            R.layout.lesson_investment_securities,
            R.layout.lesson_risk_return,
            R.layout.lesson_portfolio_construction,
            R.layout.lesson_investment_strategies,
            R.layout.lesson_tax_considerations
    };

    public static final int[] LESSON_BUTTON_TEXT_VALUES = {
            R.string.investment_basics_learn,
            R.string.financial_goals_learn,
            R.string.investment_securities_learn,
            R.string.risk_return_learn,
            R.string.portfolio_construction_learn,
            R.string.investment_strategies_learn,
            R.string.tax_considerations_learn
    };

    public static final String LESSON_COMPLETE_INTENT_TAG = "lesson-complete";
    public static final String LESSON_ID_INTENT_TAG = "lesson-id";

    public static final int BUTTON_COMPLETE_STYLE =
            com.google.android.material.R.style.Widget_Material3_Button_ElevatedButton;
}
