package com.numbereater.investmentapp

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.HttpUrl
import java.lang.RuntimeException

class StockDataInterface() {
    companion object {
        private const val API_HOST = "www.alphavantage.co"
        private const val API_KEY = "QC3R0OUQIMVN0N4L"
        private const val FUNCTION_TIME_SERIES_DAILY = "TIME_SERIES_DAILY"
    }

    private val client = OkHttpClient()

    fun getDailyFromTicker(ticker: String): DailyPriceResponse? {
        val url = HttpUrl.Builder()
            .scheme("https")
            .host(API_HOST)
            .addPathSegment("query")
            .addQueryParameter("function", FUNCTION_TIME_SERIES_DAILY)
            .addQueryParameter("symbol", ticker)
            .addQueryParameter("apikey", API_KEY)
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        val gson = Gson()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            Log.e("StockDataInterface", "Request failed with code ${response.code()}")
            return null
        }

        val responseBody = response.body().string()
        println(responseBody)

        val responseObject = gson.fromJson(responseBody, JsonObject::class.java)
        if (responseObject == null) {
            Log.e("StockDataInterface", "Gson failed to parse from response body")
            return null
        }
        if (!isValidResponse(responseObject)) {
            Log.e("StockDataInterface", "API Returned Error Code")
            return null
        }

        var timeSeries = responseObject.get("Time Series (Daily)")
        if (timeSeries == null) {
            println(responseBody)
            return null
        } else {
            timeSeries = timeSeries.asJsonObject
        }

        val dayStocks = arrayListOf<DayStock>()
        for (date in timeSeries.asMap().keys) {
            val dateObj = timeSeries.get(date).asJsonObject
            dayStocks.add(
                DayStock(
                    date,
                    dateObj.get("1. open").asString,
                    dateObj.get("2. high").asString,
                    dateObj.get("3. low").asString,
                    dateObj.get("4. close").asString,
                    dateObj.get("5. volume").asString,
                )
            )
        }


        return DailyPriceResponse(
            ticker,
            dayStocks
        )
    }

    private fun isValidResponse(data: JsonObject): Boolean {
        return (data.get("Error Message") == null)
    }
}