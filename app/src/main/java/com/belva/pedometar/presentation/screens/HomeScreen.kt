package com.belva.pedometar.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.belva.pedometar.presentation.navigation.SetupBottomBar
import com.belva.pedometar.presentation.navigation.SetupNavHost

@Composable
fun HomeScreen() {
//    Text(text = "Home Screen")

    val homeNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            SetupBottomBar(navController = homeNavController)
        }
    ) { innerPadding ->
        SetupNavHost(
            navController = homeNavController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}