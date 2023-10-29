package com.numbereater.investmentapp

data class AggregateBarsResponse(
    val symbol: String,
    val status: String,
    val days: Array<StockDayData?>
)