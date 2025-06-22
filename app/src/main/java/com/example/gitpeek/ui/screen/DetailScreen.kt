package com.example.gitpeek.ui.screen

import com.example.gitpeek.ui.mvi.UserIntent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.gitpeek.ui.mvi.UserViewModel

@Composable
fun DetailScreen(username: String, viewModel: UserViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(username) {
        viewModel.onIntent(UserIntent.Search(username))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text("Error: ${state.error}")
            }

            state.user != null -> {
                val user = state.user!!
                AsyncImage(
                    model = user.avatarUrl,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Username: ${user.login}")
                Text("Name: ${user.name ?: "N/A"}")
                Text("Location: ${user.location ?: "N/A"}")
                Text("Repos: ${user.publicRepos}")
            }
        }
    }
}
