package com.example.gitpeek.ui.details.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.gitpeek.ui.details.mvi.DetailIntent
import com.example.gitpeek.ui.details.mvi.DetailViewModel

@Composable
fun DetailScreen(username: String, viewModel: DetailViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(DetailIntent.LoadUserDetails(username))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            state.error != null -> {
                Text("Error: ${state.error}")
            }

            state.user != null -> {
                val user = state.user!!

                AsyncImage(
                    model = user.avatarUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Username: ${user.login}", style = MaterialTheme.typography.titleMedium)
                Text("Name: ${user.name ?: "N/A"}")
                Text("Location: ${user.location ?: "N/A"}")
                Text("Public Repos: ${user.publicRepos}")
                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider()
                Text("Repositories:", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(state.repos) { repo ->
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("🔹 ${repo.name}", style = MaterialTheme.typography.titleSmall)
                            if (!repo.description.isNullOrEmpty()) {
                                Text(" ${repo.description}", style = MaterialTheme.typography.bodySmall)
                            }
                            Text("Updated: ${repo.updatedAt}", style = MaterialTheme.typography.bodySmall)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
        }
    }
}
