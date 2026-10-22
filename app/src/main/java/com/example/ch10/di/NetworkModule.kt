package com.example.ch10.di

import com.example.ch10.BuildConfig
import com.example.ch10.data.remote.API_KEY_HEADER
import com.example.ch10.data.remote.ApiKeyInterceptor
import com.example.ch10.data.remote.NewsApiService
import com.example.ch10.data.remote.newsJson
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{
    @Provides
    @Singleton
    fun provideJson() : Json = Json {
        ignoreUnknownKeys = true    // toleran terhadap field baru dari server
        coerceInputValues = true    // null -> nilai default jika properti non-null
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
            redactHeader(API_KEY_HEADER)      // key tidak ikut tercetak di Logcat
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(
        client: OkHttpClient,
        json: Json = newsJson
    ): NewsApiService = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(NewsApiService::class.java)
}