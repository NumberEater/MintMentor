package com.numbereater.investmentapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class StocksFragment : BottomNavigationFragment() {
    override val bottomNavigationButtonId = R.id.stocks_action

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_stocks, container, false)

        layout.findViewById<Button>(R.id.stock_lookup_button).setOnClickListener {
            startActivity(Intent(context, StockLookupActivity::class.java))
        }

        layout.findViewById<Button>(R.id.profit_calculator_button).setOnClickListener {
            startActivity(Intent(context, ProfitCalculatorActivity::class.java))
        }

        return layout
    }
}