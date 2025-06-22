package com.example.gitpeek.ui.details.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.gitpeek.ui.common.AppTopBar
import com.example.gitpeek.ui.details.mvi.DetailIntent
import com.example.gitpeek.ui.details.mvi.DetailViewModel
import com.example.gitpeek.util.formatIsoDate

@Composable
fun DetailScreen(
    username: String,
    navController: NavHostController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(DetailIntent.LoadUserDetails(username))
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = username,
                showBack = true,
                onBackClick = { navController.popBackStack() },
                icon = Icons.Default.Person)
        }
    ) { paddingValues ->

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("❌ Error: ${state.error}", color = MaterialTheme.colorScheme.error)
                }
            }

            state.user != null -> {
                val user = state.user!!

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        top = paddingValues.calculateTopPadding() + 16.dp,
                        bottom = paddingValues.calculateBottomPadding() + 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.large,
                            elevation = CardDefaults.cardElevation(6.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = user.avatarUrl,
                                    contentDescription = "Avatar",
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    user.name ?: "N/A",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    "@${user.login}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("📍 ${user.location ?: "Unknown"}")
                                Text("📦 ${user.publicRepos} Public Repos")
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "📁 Repositories",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    items(state.repos) { repo ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(repo.name, style = MaterialTheme.typography.titleSmall)
                                if (!repo.description.isNullOrEmpty()) {
                                    Text(
                                        repo.description,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "🕒 Updated: ${formatIsoDate(repo.updatedAt)}",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
