package com.example.gitpeek.navigation

sealed class Screen(val route: String) {
    object Search : Screen("search")
    object Detail : Screen("detail/{username}") {
        fun createRoute(username: String) = "detail/$username"
    }
}