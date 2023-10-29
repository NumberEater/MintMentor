package com.numbereater.investmentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.TextView
import java.lang.RuntimeException

class ViewInvestmentPlatformActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.template_investment_platform)

        val platformId = intent.getIntExtra("platform-id", -1)
        if (platformId == -1) {
            throw RuntimeException("No platform-id passed to ViewInvestmentPlatformActivity.")
        }

        val platform = Constants.INVESTING_PLATFORMS[platformId]

        findViewById<TextView>(R.id.platform_title).setText(platform.nameResource)
        findViewById<ImageView>(R.id.platform_logo).setImageResource(platform.logoDrawableResource)
        findViewById<TextView>(R.id.platform_benefits).setText(platform.prosResource)
        findViewById<TextView>(R.id.platform_cons).setText(platform.consResource)

        val linkText = findViewById<TextView>(R.id.platform_link)
        linkText.setText(platform.linkResource)
        linkText.movementMethod = LinkMovementMethod.getInstance()
    }
}