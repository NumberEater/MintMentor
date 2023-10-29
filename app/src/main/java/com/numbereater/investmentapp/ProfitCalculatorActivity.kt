package com.numbereater.investmentapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class ProfitCalculatorActivity : AppCompatActivity() {
    private lateinit var stockSymbolInput: EditText
    private lateinit var priceInput: EditText
    private lateinit var quantityInput: EditText
    private lateinit var calculateButton: Button

    private lateinit var percentLostText: TextView
    private lateinit var valueLostText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profit_calculator)

        initLayoutElements()

        calculateButton.setOnClickListener { calculateButtonAction() }
    }

    private fun initLayoutElements() {
        stockSymbolInput = findViewById(R.id.ticker_symbol_input)
        priceInput = findViewById(R.id.stock_price_input)
        quantityInput = findViewById(R.id.quantity_purchased_input)
        calculateButton = findViewById(R.id.calculate_button)

        percentLostText = findViewById(R.id.percent_lost_text)
        valueLostText = findViewById(R.id.value_lost_text)
    }

    private fun calculateButtonAction() {
        val purchasePrice: Float
        val quantityPurchased: Int

        val tickerValue = stockSymbolInput.text.toString().also(::println)
        val purchasePriceEditable = priceInput.text
        val quantityPurchasedEditable = quantityInput.text
        if (
            tickerValue.isEmpty() ||
            purchasePriceEditable.isNullOrEmpty() ||
            quantityPurchasedEditable.isNullOrEmpty()) {
            return
        } else {
            purchasePrice = purchasePriceEditable.toString().toFloat()
            quantityPurchased = quantityPurchasedEditable.toString().toInt()
        }

        Thread {
            val response = StockDataAPI().getYesterdayOpenClose(tickerValue)

            when (response.status) {
                "OK" -> {
                    val valueChange = (response.close - purchasePrice) * quantityPurchased.toFloat()
                    val percentChange = ((response.close - purchasePrice)/purchasePrice) * 100f
                    runOnUiThread {
                        setValueChange(valueChange)
                        setPercentChange(percentChange)
                    }
                }
                "NOT_FOUND" -> {
                    runOnUiThread {
                        clearResultText()
                        Toast.makeText(applicationContext, "Symbol Not Found", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> runOnUiThread { Toast.makeText(applicationContext, response.status, Toast.LENGTH_LONG).show() }
            }

        }.start()
    }
    
    private fun setValueChange(value: Float) {
        if (value > 0) {
            valueLostText.text = String.format("+$%.2f", value)
            valueLostText.setTextColor(Color.argb(1.0f, 0.2f, 0.8f, 0.2f))
        } else if (value < 0) {

            valueLostText.text = String.format("-$%.2f", value*-1)
            valueLostText.setTextColor(Color.argb(1.0f, 0.8f, 0.2f, 0.2f))
        }
        else {
            valueLostText.text = String.format("$%.2f", value)
        }
    }
    
    private fun setPercentChange(value: Float) {
        if (value > 0) {
            percentLostText.text = String.format("%.2f", value) + "%"
            percentLostText.setTextColor(Color.argb(1.0f, 0.2f, 0.8f, 0.2f))
        } else if (value < 0) {
            percentLostText.text = String.format("%.2f", value) + "%"
            percentLostText.setTextColor(Color.argb(1.0f, 0.8f, 0.2f, 0.2f))
        } else {
            percentLostText.text = String.format("%.2f", value) + "%"
        }
    }

    private fun clearResultText() {
        valueLostText.text = ""
        percentLostText.text = ""
    }

}