package com.example.stocktracker.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocktracker.data.model.StockQuote
import com.example.stocktracker.data.network.StockApi
import kotlinx.coroutines.launch

sealed interface StockUiState { //Why sealed interface-Because it forces the UI to handle all states
    object Loading : StockUiState//represent the app is currently fetching stock data
    object Error : StockUiState //represent something went wrong,Show error message
    data class Success(val stock: StockQuote) : StockUiState //API call succeeded,It contains:StockQuote object,Display stock values
}


//This class acts as the logic controller for the UI
class StockViewModel : ViewModel() {

    var stockUiState: StockUiState by mutableStateOf(StockUiState.Loading)//Whenever this variable changes:The UI immediately updates.
        private set //Only the ViewModel can change UI.

    private val apiKey ="d6jkfvhr01qkvh5qa5hgd6jkfvhr01qkvh5qa5i0"

    init { //When HomeScreen appears/ViewModel is created/
        fetchStock("AAPL") //fetchStock("AAPL") runs automatically
    }

    fun fetchStock(symbol: String) {

        stockUiState = StockUiState.Loading

        viewModelScope.launch {// start to run in the background
            try {
                val stockApi = StockApi.getInstance()//Gets the Retrofit API service.-->StockApi.kt
                val result = stockApi.getStockQuote(symbol, apiKey) //calls this function defined in StockApi.kt:triggers the API request.
                stockUiState = StockUiState.Success(result) //The UI immediately updates.
            } catch (e: Exception) {
                stockUiState = StockUiState.Error
            }
        }
    }
}