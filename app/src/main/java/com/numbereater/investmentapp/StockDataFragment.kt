package com.numbereater.investmentapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment

class StockDataFragment(private val data: DailyPriceResponse) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_stock_data, container, false)

        layout.findViewById<TextView>(R.id.ticker_title).text = data.symbol

        val dataTable = layout.findViewById<TableLayout>(R.id.stock_data_table)

        for (i in 0 until 2) {
            val params = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(10, 0, 10, 0)

            val open = TextView(context).also {
                it.text = i.toString()
                it.layoutParams = params
            }
            
            val high = TextView(context).also {
                it.text = i.toString()
                it.layoutParams = params
            }

            val low = TextView(context).also {
                it.text = i.toString()
                it.layoutParams = params
            }

            val close = TextView(context).also {
                it.text = i.toString()
                it.layoutParams = params
            }

            val volume = TextView(context).also {
                it.text = i.toString()
                it.layoutParams = params
            }


            val row = TableRow(context)
            row.addView(open)
            row.addView(high)
            row.addView(low)
            row.addView(close)
            row.addView(volume)

            dataTable.addView(row)
        }
        return layout
    }
}