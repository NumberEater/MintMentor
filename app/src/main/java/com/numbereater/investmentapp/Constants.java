package com.numbereater.investmentapp;

public class Constants {
    /* An array of layouts where the index of each
    layout corresponds to the lesson number */
    public static final int[] LESSONS = {
            R.layout.layout_investment_basics,
            R.layout.layout_setting_financial_goals,
            R.layout.layout_investment_securities,
            R.layout.layout_risk_return,
            R.layout.layout_portfolio_construction,
            R.layout.layout_investment_strategies,
            R.layout.layout_tax_considerations,
            R.layout.layout_investing_platforms
    };

    public static final int[] LESSON_BUTTON_TEXT_VALUES = {
            R.string.investment_basics_learn,
            R.string.financial_goals_learn,
            R.string.investment_securities_learn,
            R.string.risk_return_learn,
            R.string.portfolio_construction_learn,
            R.string.investment_strategies_learn,
            R.string.tax_considerations_learn,
            R.string.investing_platforms_learn
    };

    public static final int BUTTON_COMPLETE_STYLE =
            com.google.android.material.R.style.Widget_Material3_Button_ElevatedButton;
}
