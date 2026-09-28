package com.example.ch06.profile

import com.example.ch06.profile.Profile

data class ProfileUiState(
    val isLoading: Boolean = false,
    val userProfile: Profile,
    val errorMessage: String? = null
){
    val username: String get() = userProfile.username
    val notificationStatus: Boolean get() = userProfile.notificationEnabled
}
