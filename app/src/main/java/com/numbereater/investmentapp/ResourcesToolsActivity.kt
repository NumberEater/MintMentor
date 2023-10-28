package com.numbereater.investmentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView

class ResourcesToolsActivity : AppCompatActivity() {
    private lateinit var websiteLinkViews: Array<TextView>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resources_tools)

        websiteLinkViews = arrayOf<TextView>(
            findViewById(R.id.yahoo_finance_desc),
            findViewById(R.id.market_watch_desc),
            findViewById(R.id.cnbc_desc),
            findViewById(R.id.federal_reserve_desc),
            findViewById(R.id.investopedia_desc)
        )
        makeWebsiteLinksClickable()
    }

    private fun makeWebsiteLinksClickable() {
        for (link in websiteLinkViews) {
            link.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}