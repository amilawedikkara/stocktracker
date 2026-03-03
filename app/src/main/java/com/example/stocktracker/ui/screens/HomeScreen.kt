package com.example.stocktracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.stocktracker.viewmodel.StockUiState
import com.example.stocktracker.viewmodel.StockViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val stockViewModel: StockViewModel = viewModel()
    val uiState = stockViewModel.stockUiState
    val symbolState = androidx.compose.runtime.remember {
        androidx.compose.runtime.mutableStateOf("AAPL")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stock Tracker") }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {

            when (uiState) {

                is StockUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is StockUiState.Error -> {
                    Text("Error loading stock data")
                }

                is StockUiState.Success -> {

                    val stock = uiState.stock

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        OutlinedTextField(
                            value = symbolState.value,
                            onValueChange = { symbolState.value = it },
                            label = { Text("Stock Symbol") }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { stockViewModel.fetchStock(symbolState.value) }
                        ) {
                            Text("Load Stock")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Current Price: ${stock.c}",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("High: ${stock.h}")
                        Text("Low: ${stock.l}")
                        Text("Open: ${stock.o}")
                        Text("Previous Close: ${stock.pc}")

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { navController.navigate("info") }
                        ) {
                            Text("Go to Info")
                        }
                    }
                }
            }
        }
    }
}
