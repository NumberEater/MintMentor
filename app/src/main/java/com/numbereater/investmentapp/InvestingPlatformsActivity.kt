package com.numbereater.investmentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class InvestingPlatformsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_investing_platforms)

        val buttons = arrayOf<Button>(
            findViewById(R.id.robinhood_button),
            findViewById(R.id.etrade_button),
            findViewById(R.id.fidelity_button),
            findViewById(R.id.ameritrade_button),
            findViewById(R.id.m1_button),
            findViewById(R.id.merrill_button)
        )

        for (i in buttons.indices) {
            buttons[i].setOnClickListener {
                val intent = Intent(applicationContext, ViewInvestmentPlatformActivity::class.java)
                intent.putExtra("platform-id", i)
                startActivity(intent)
            }
        }
    }
}