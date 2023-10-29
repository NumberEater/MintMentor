package com.numbereater.investmentapp

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.okhttp.HttpUrl
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import java.util.Calendar
import java.util.GregorianCalendar

class StockDataAPI {
    companion object {
        private const val POLYGON_HOST = "api.polygon.io"
        private const val POLYGON_KEY = "GQB8oIKB6B7GYxLzYcdrmJ0kRFGWyEIH"
    }

    fun getPastMonthData(ticker: String): AggregateBarsResponse {
        val client = OkHttpClient()
        val response = client.newCall(createPastMonthRequest(ticker)).execute()

        val gson = Gson()

        val responseBody = response.body().string()
        val topLevelResponse = gson.fromJson(responseBody, JsonObject::class.java)

        val status = topLevelResponse.get("status").asString
        val numResults = topLevelResponse.get("resultsCount").asInt

        if (numResults == 0) {
            return AggregateBarsResponse("", "NOT_FOUND", arrayOf())
        }

        val numberResults = topLevelResponse.get("resultsCount").asInt

        val resultsArray = topLevelResponse.get("results").asJsonArray.asIterable()
        val outputArray = arrayOfNulls<StockDayData>(numberResults)
        resultsArray.forEachIndexed { i, result ->
            outputArray[i] = gson.fromJson(result, StockDayData::class.java)
        }
        return AggregateBarsResponse(ticker, status, outputArray)
    }

    private fun createPastMonthRequest(ticker: String): Request {
        return Request.Builder()
            .url(createPastMonthUrl(ticker))
            .get()
            .build()
    }

    private fun createPastMonthUrl(ticker: String): HttpUrl {
        return HttpUrl.Builder()
            .scheme("https")
            .host(POLYGON_HOST)
            .addPathSegment("v2/aggs/ticker")
            .addPathSegment(ticker)
            .addPathSegment("range/1/day")
            .addPathSegment(getOneMonthAgoDate())
            .addPathSegment(getYesterdayDate())
            .addQueryParameter("apiKey", POLYGON_KEY)
            .build()
    }

    fun getCompanyNameFromTicker(ticker: String): String? {
        val client = OkHttpClient()
        val response = client.newCall(createCompanyNameRequest(ticker)).execute()
        val gson = Gson()

        val topLevelResult = gson.fromJson(response.body().string(), JsonObject::class.java)

        val resultsArray = topLevelResult.get("results").asJsonArray

        return if (resultsArray.isEmpty) {
            null
        } else {
            resultsArray.get(0).asJsonObject.get("name").asString
        }
    }

    private fun createCompanyNameRequest(ticker: String): Request {
        return Request.Builder()
            .url(createCompanyNameUrl(ticker))
            .get()
            .build()
    }

    private fun createCompanyNameUrl(ticker: String): HttpUrl {
        return HttpUrl.Builder()
            .scheme("https")
            .host(POLYGON_HOST)
            .addPathSegment("v3/reference/tickers")
            .addQueryParameter("ticker", ticker)
            .addQueryParameter("apiKey", POLYGON_KEY)
            .build()
    }

    fun getYesterdayOpenClose(ticker: String): OpenCloseResponse {
        val client = OkHttpClient()

        val request = createOpenCloseRequest(ticker)

        val response = client.newCall(request).execute()
        val body = response.body().string().also(::println)
        return Gson().fromJson(body, OpenCloseResponse::class.java)
    }

    private fun createOpenCloseRequest(ticker: String): Request {
        return Request.Builder()
            .url(createOpenCloseUrl(ticker))
            .get()
            .build()
    }

    private fun createOpenCloseUrl(ticker: String): HttpUrl {
        return HttpUrl.Builder()
            .scheme("https")
            .host(POLYGON_HOST)
            .addPathSegment("v1/open-close")
            .addPathSegment(ticker)
            .addPathSegment(getYesterdayDate())
            .addQueryParameter("adjusted", "true")
            .addQueryParameter("apiKey", POLYGON_KEY)
            .build()
    }

    private fun getOneMonthAgoDate(): String {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val date = GregorianCalendar(year, month, day)
        date.add(Calendar.DATE, -91)

        return String.format("%04d-%02d-%02d",
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH)+1,
            date.get(Calendar.DAY_OF_MONTH))
    }

    private fun getYesterdayDate(): String {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val date = GregorianCalendar(year, month, day)
        date.add(Calendar.DATE, -2)

        return String.format("%04d-%02d-%02d",
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH)+1,
            date.get(Calendar.DAY_OF_MONTH))
    }
}