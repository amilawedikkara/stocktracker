package com.example.stocktracker.data.network

import com.example.stocktracker.data.model.StockQuote
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://finnhub.io/api/v1/"

interface StockApi {

    @GET("quote")
    suspend fun getStockQuote(
        @Query("symbol") symbol: String,
        @Query("token") token: String
    ): StockQuote

    companion object {

        private var stockService: StockApi? = null

        fun getInstance(): StockApi {

            if (stockService == null) {
                stockService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(StockApi::class.java)
            }

            return stockService!!
        }
    }
}