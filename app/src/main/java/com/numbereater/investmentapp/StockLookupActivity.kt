package com.numbereater.investmentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import java.util.Locale

class StockLookupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_lookup)

        initLayoutElements()

        setTickerInputAction()
    }

    private lateinit var tickerSymbolInput: EditText
    private lateinit var tickerSymbolTitle: TextView
    private lateinit var stockGraphLinkText: TextView
    private lateinit var stockHistoryTable: TableLayout

    private fun initLayoutElements() {
        tickerSymbolInput = findViewById(R.id.ticker_symbol_input)
        tickerSymbolTitle = findViewById(R.id.ticker_symbol_title)

        stockGraphLinkText = findViewById(R.id.stock_graph_link)
        stockGraphLinkText.movementMethod = LinkMovementMethod.getInstance()

        stockHistoryTable = findViewById(R.id.stock_data_table)
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

        val searchInput = tickerSymbolInput.text.toString().uppercase(Locale.ROOT)
        tickerSymbolInput.setText("")
        Thread {
            Looper.prepare()

            val data = StockDataAPI().getPastMonthData(searchInput)
            when (data.status) {
                "OK" -> {
                    val companyName = StockDataAPI().getCompanyNameFromTicker(data.symbol)
                    runOnUiThread {
                        displayData(data, companyName)
                    }
                }
                "NOT_FOUND" -> {
                    Toast.makeText(applicationContext, "Failed to find symbol.", Toast.LENGTH_SHORT).show()
                    runOnUiThread {
                        setTickerTitle("Not Found")
                        clearGraphLink()
                        clearHistoryTable()
                    }
                }

                else -> runOnUiThread {
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

        val openText = TextView(applicationContext)
        openText.text = String.format("%.2f", day.open)
        openText.textAlignment = View.TEXT_ALIGNMENT_CENTER
        openText.layoutParams = params

        val highText = TextView(applicationContext)
        highText.text = String.format("%.2f", day.high)
        highText.textAlignment = View.TEXT_ALIGNMENT_CENTER
        highText.layoutParams = params

        val lowText = TextView(applicationContext)
        lowText.text = String.format("%.2f", day.low)
        lowText.textAlignment = View.TEXT_ALIGNMENT_CENTER
        lowText.layoutParams = params

        val closeText = TextView(applicationContext)
        closeText.text = String.format("%.2f", day.close)
        closeText.textAlignment = View.TEXT_ALIGNMENT_CENTER
        closeText.layoutParams = params

        val row = TableRow(applicationContext)
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
        return Html.fromHtml("<a href=\"https://finance.yahoo.com/quote/$ticker?p=${ticker}&tsrc=fin-srch\">View Graph and More Info</a>", Html.FROM_HTML_MODE_LEGACY)
    }
}
