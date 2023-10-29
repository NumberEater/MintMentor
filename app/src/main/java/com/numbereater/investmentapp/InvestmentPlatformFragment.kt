package com.numbereater.investmentapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class InvestmentPlatformFragment(private val platform: InvestingPlatform) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflatedView = inflater.inflate(R.layout.template_investment_platform, container, false)

        inflatedView.findViewById<TextView>(R.id.platform_title).setText(platform.nameResource)
        inflatedView.findViewById<ImageView>(R.id.platform_logo).setImageResource(platform.logoDrawableResource)
        inflatedView.findViewById<TextView>(R.id.platform_benefits).setText(platform.prosResource)
        inflatedView.findViewById<TextView>(R.id.platform_cons).setText(platform.consResource)

        return inflatedView
    }
}