package com.numbereater.investmentapp

import com.google.gson.JsonObject

data class DailyPriceResponse(
    val symbol: String,
    val timeSeries: ArrayList<DayStock>
)