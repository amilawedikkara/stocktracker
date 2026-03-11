package com.example.stocktracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stocktracker.viewmodel.CompanyUiState
import com.example.stocktracker.viewmodel.StockViewModel
import androidx.compose.runtime.LaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(navController: NavController, symbol: String) {

    val stockViewModel: StockViewModel = viewModel()
    val companyState = stockViewModel.companyUiState

    LaunchedEffect(Unit) {
        stockViewModel.fetchCompany(symbol)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Info") }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (companyState) {

                is CompanyUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is CompanyUiState.Error -> {
                    Text("Error loading company data")
                }

                is CompanyUiState.Success -> {

                    val company = companyState.company

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Text(
                            text = company.name,
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Ticker: ${company.ticker}")
                        Text("Country: ${company.country}")
                        Text("Exchange: ${company.exchange}")
                        Text("Website: ${company.weburl}")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigateUp() }
            ) {
                Text("Back")
            }
        }
    }
}