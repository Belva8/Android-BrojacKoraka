package com.belva.pedometar.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.belva.pedometar.presentation.screens.bottom_bar.PedometerScreen
import com.belva.pedometar.presentation.screens.bottom_bar.ProfileScreen
import com.belva.pedometar.presentation.screens.bottom_bar.RecordScreen

//navigacija kroz apk


@Composable
fun SetupNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.PedometerScreen.route
    ) {
        composable(Screen.PedometerScreen.route) {
            PedometerScreen()
        }
        composable(Screen.RecordScreen.route) {
            RecordScreen()
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen()
        }
    }


}