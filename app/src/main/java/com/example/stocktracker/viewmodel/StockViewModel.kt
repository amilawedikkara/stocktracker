package com.example.stocktracker.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocktracker.data.model.StockQuote
import com.example.stocktracker.data.network.StockApi
import kotlinx.coroutines.launch

sealed interface StockUiState {
    object Loading : StockUiState
    object Error : StockUiState
    data class Success(val stock: StockQuote) : StockUiState
}

class StockViewModel : ViewModel() {

    var stockUiState: StockUiState by mutableStateOf(StockUiState.Loading)
        private set

    private val apiKey ="d6jkfvhr01qkvh5qa5hgd6jkfvhr01qkvh5qa5i0"

    init {
        fetchStock("AAPL")
    }

    fun fetchStock(symbol: String) {

        stockUiState = StockUiState.Loading

        viewModelScope.launch {
            try {
                val stockApi = StockApi.getInstance()
                val result = stockApi.getStockQuote(symbol, apiKey)
                stockUiState = StockUiState.Success(result)
            } catch (e: Exception) {
                stockUiState = StockUiState.Error
            }
        }
    }
}