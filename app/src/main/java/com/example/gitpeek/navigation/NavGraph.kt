package com.example.gitpeek.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gitpeek.ui.details.screen.DetailScreen
import com.example.gitpeek.ui.screen.SearchScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Search.route
    ) {
        composable(Screen.Search.route) {
            SearchScreen(navController)
        }
        composable(Screen.Detail.route) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            if (username != null) {
                DetailScreen(username)
            }
        }
    }
}