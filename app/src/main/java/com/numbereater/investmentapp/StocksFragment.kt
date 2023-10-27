package com.numbereater.investmentapp

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import com.google.gson.JsonObject

class StocksFragment : BottomNavigationFragment() {
    override val bottomNavigationButtonId = R.id.stocks_action

    private lateinit var tickerSymbolInput: EditText
    private lateinit var fragmentContainer: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_stocks, container, false)
        initLayoutElements(layout)

        setTickerInputAction()

        return layout
    }

    private fun initLayoutElements(layout: View) {
        tickerSymbolInput = layout.findViewById(R.id.ticker_symbol_input)
        fragmentContainer = layout.findViewById(R.id.stock_info_fragment_container)
    }

    private fun setTickerInputAction() {
        tickerSymbolInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                tickerInputAction()
            }
            false
        }
    }

    private fun tickerInputAction() {
        Thread {
            Looper.prepare()
            val data = StockDataInterface().getDailyFromTicker(tickerSymbolInput.text.toString())
            if (data != null) {
                displayData(data)
            } else {
                Toast.makeText(context, "Failed to find symbol.", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun displayData(data: DailyPriceResponse) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.stock_info_fragment_container, StockDataFragment(data))
        transaction.commit()
    }
}