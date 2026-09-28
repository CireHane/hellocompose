package com.example.ch06.profile

import com.example.ch06.profile.Profile
import kotlinx.coroutines.delay

// Abstraksi sumber data — ViewModel tidak perlu tahu datanya dari mana
interface ProfileRepository {
    suspend fun getProfile(): Profile
    fun getCachedProfile(): Profile
}

// Implementasi palsu untuk latihan; nanti bisa diganti Retrofit/Room
class FakeProfileRepository : ProfileRepository {
    private val cached = Profile(
        "Eric S.I.",
        false
    )

    override suspend fun getProfile(): Profile {
        delay(1200) // simulasi latensi jaringan
        return cached
    }

    override fun getCachedProfile(): Profile = cached
}
