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

    val stockViewModel: StockViewModel = viewModel() //get stockViewModel instant for this screen
    val uiState = stockViewModel.stockUiState //reads the current screen state from the ViewModel
    val symbolState = androidx.compose.runtime.remember {
        androidx.compose.runtime.mutableStateOf("AAPL")//reads the current screen state from the ViewModel
    }
    Scaffold( //gives the screen a standard Material layout structure.
        topBar = { //screen gets a top bar
            TopAppBar(
                title = { Text("Stock Tracker") }
            )
        }
    ) { innerPadding -> //innerPadding is provided by Scaffold so your content does not overlap with the top bar

        Box( //Box is a layout container
            modifier = Modifier
                .fillMaxSize()//fill the full screen
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {

            when (uiState) { //UI depending on current state

                is StockUiState.Loading -> { //If data is still being fetched from the API,show a loading spinner
                    CircularProgressIndicator()
                }

                is StockUiState.Error -> { //If API request fails: show an error message
                    Text("Network error. Please try again.")
                }

                is StockUiState.Success -> { //If API request succeeds:uiState contains real stock data

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
                            onClick = {
                                if (symbolState.value.isNotBlank()) {
                                    stockViewModel.fetchStock(symbolState.value)
                                }
                            }
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
                            onClick = { navController.navigate("info/${symbolState.value}") }//navigate to the "info" route
                        ) {
                            Text("Go to Info")
                        }
                    }
                }
            }
        }
    }
}
