package com.example.ch06.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.ch06.profile.ProfileRepository
import com.example.ch06.profile.ProfileUiState


class ProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState(isLoading = true, userProfile = Profile("", false)))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            runCatching { repository.getProfile() }
                .onSuccess { profile ->
                    _uiState.update { it.copy(isLoading = false, userProfile = profile) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Gagal memuat artikel"
                        )
                    }
                }
        }
    }

    fun onUsernameChange(newUsername: String){
        _uiState.update {
            it.copy(userProfile = it.userProfile.copy(username = newUsername))
        }
    }
    fun onToggleNotification(){
        _uiState.update {
            it.copy(userProfile = it.userProfile.copy(notificationEnabled = !it.userProfile.notificationEnabled))
        }
    }
}
