package com.example.gitpeek.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitpeek.domain.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state

    fun onIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.Search -> fetchUser(intent.username)
            UserIntent.Retry     -> _state.update { it.copy(error = null) }
        }
    }

    private fun fetchUser(username: String) = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true, error = null)
        repository.getUser(username)
            .onSuccess { user -> _state.value = UserState(user = user) }
            .onFailure { e -> _state.value = UserState(error = e.message) }
    }
}
