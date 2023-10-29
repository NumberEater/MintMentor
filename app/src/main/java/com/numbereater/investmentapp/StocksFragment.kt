package com.numbereater.investmentapp

import android.os.Bundle
import android.os.Looper
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast


class StocksFragment : BottomNavigationFragment() {
    override val bottomNavigationButtonId = R.id.stocks_action

    private lateinit var tickerSymbolInput: EditText
    private lateinit var tickerSymbolTitle: TextView
    private lateinit var stockGraphLinkText: TextView
    private lateinit var stockHistoryTable: TableLayout

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
        tickerSymbolTitle = layout.findViewById(R.id.ticker_symbol_title)

        stockGraphLinkText = layout.findViewById(R.id.stock_graph_link)
        stockGraphLinkText.movementMethod = LinkMovementMethod.getInstance()

        stockHistoryTable = layout.findViewById(R.id.stock_data_table)
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
        if (tickerSymbolInput.text.isBlank()) {
            return
        }

        val searchInput = tickerSymbolInput.text.toString()
        tickerSymbolInput.setText("")
        Thread {
            Looper.prepare()

            val data = StockDataAPI().getPastMonthData(searchInput)
            when (data.status) {
                "OK" -> {
                    val companyName = StockDataAPI().getCompanyNameFromTicker(data.symbol)
                    activity?.runOnUiThread {
                        displayData(data, companyName)
                    }
                }
                "NOT_FOUND" -> {
                    Toast.makeText(context, "Failed to find symbol.", Toast.LENGTH_SHORT).show()
                    activity?.runOnUiThread {
                        setTickerTitle("Not Found")
                        clearGraphLink()
                        clearHistoryTable()
                    }
                }

                else -> activity?.runOnUiThread {
                    setTickerTitle("Error")
                    clearHistoryTable()
                }
            }

        }.start()
    }

    private fun displayData(data: AggregateBarsResponse, companyName: String?) {
        if (companyName != null) {
            setTickerTitle(companyName)
        } else {
            setTickerTitle(data.symbol)
        }
        setGraphLink(generateYahooGraphLink(data.symbol))

        clearHistoryTable()
        for (day in data.days) {
            if (day != null) {
                addDayToStockHistoryTable(day)
            }
        }
    }

    private fun clearHistoryTable() {
        val length = stockHistoryTable.childCount
        stockHistoryTable.removeViews(1, length-1)
    }

    private fun addDayToStockHistoryTable(day: StockDayData) {
        val params = TableRow.LayoutParams(
            0,
            TableRow.LayoutParams.MATCH_PARENT,
            0.25f
        )

        val openText = TextView(context)
        openText.text = String.format("%.2f", day.open)
        openText.textAlignment = View.TEXT_ALIGNMENT_CENTER
        openText.layoutParams = params

        val highText = TextView(context)
        highText.text = String.format("%.2f", day.high)
        highText.textAlignment = View.TEXT_ALIGNMENT_CENTER
        highText.layoutParams = params

        val lowText = TextView(context)
        lowText.text = String.format("%.2f", day.low)
        lowText.textAlignment = View.TEXT_ALIGNMENT_CENTER
        lowText.layoutParams = params

        val closeText = TextView(context)
        closeText.text = String.format("%.2f", day.close)
        closeText.textAlignment = View.TEXT_ALIGNMENT_CENTER
        closeText.layoutParams = params

        val row = TableRow(context)
        row.gravity = Gravity.CENTER_HORIZONTAL
        row.addView(openText)
        row.addView(highText)
        row.addView(lowText)
        row.addView(closeText)

        stockHistoryTable.addView(row)
    }

    private fun setTickerTitle(value: String) {
        tickerSymbolTitle.text = value
    }

    private fun setGraphLink(value: Spanned) {
        stockGraphLinkText.text = value
    }

    private fun clearGraphLink() {
        stockGraphLinkText.text = ""
    }

    private fun generateYahooGraphLink(ticker: String): Spanned {
        return Html.fromHtml("<a href=\"https://finance.yahoo.com/quote/$ticker?p=${ticker}&tsrc=fin-srch\">Graph</a>", Html.FROM_HTML_MODE_LEGACY)
    }


}