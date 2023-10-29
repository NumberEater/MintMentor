package com.numbereater.investmentapp

import com.google.gson.annotations.SerializedName

data class StockDayData(
    @SerializedName("o") val open: Float,
    @SerializedName("h") val high: Float,
    @SerializedName("l") val low: Float,
    @SerializedName("c") val close: Float
)