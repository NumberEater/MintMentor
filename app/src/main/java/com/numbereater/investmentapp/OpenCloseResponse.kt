package com.numbereater.investmentapp

data class OpenCloseResponse(
    val close: Float,
    val high: Float,
    val low: Float,
    val open: Float,
    val status: String,
    val symbol: String
)