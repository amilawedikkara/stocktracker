package com.example.stocktracker.data.network

import com.example.stocktracker.data.model.StockQuote
import com.example.stocktracker.data.model.CompanyProfile
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://finnhub.io/api/v1/"//private means only this file can access
//canst means compile time constant, val means read only variable
interface StockApi {//defines a Retrofit API interface

    @GET("quote") //method-GET endpoint-quote
    suspend fun getStockQuote( //This function must run inside a coroutine
        @Query("symbol") symbol: String,
        @Query("token") token: String
    ): StockQuote //The API response should be converted into a StockQuote object

    @GET("stock/profile2")
    suspend fun getCompanyProfile(
        @Query("symbol") symbol: String,
        @Query("token") token: String
    ): CompanyProfile

    companion object {//A companion object acts like a static section of the class.

        //Retrofit Builder Section
        private var stockService: StockApi? = null //This stores the Retrofit API instance.

        fun getInstance(): StockApi { //returns the Retrofit service

            if (stockService == null) { //If API instance does not exist, then create it
                stockService = Retrofit.Builder()//Retrofit is the library responsible for HTTP communication.creates the HTTP client.
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())//Use Gson to convert JSON → Kotlin objects
                    .build()//Use Gson to convert JSON → Kotlin objects
                    .create(StockApi::class.java)//Create an implementation of the StockApi interface
            }

            return stockService!!
        }
    }
}