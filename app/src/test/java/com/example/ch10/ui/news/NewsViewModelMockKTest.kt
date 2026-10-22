package com.example.ch10.ui.news

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ch10.data.NewsRepository
import com.example.ch10.data.model.Article
import com.example.ch10.data.model.NewsCategory
import com.example.ch10.data.model.NewsFeed
import com.example.ch10.testutil.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull

// Tugas 3: uji NewsViewModel dengan MockK (bukan FakeNewsRepository).
//  - coEvery { repository.getTopHeadlines(any()) } returns Result.success(...)
//  - buat ViewModel: NewsViewModel(repository) -> init langsung memuat headline
//  - runCurrent() agar coroutine di viewModelScope selesai
//  - periksa viewModel.uiState.value, dan coVerify { ... } untuk jumlah panggilan
// Helper article(id) ada di testutil/TestData.kt. Hilt TIDAK diperlukan di sini.
@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelMockKTest {

    @get:Rule val mainRule = MainDispatcherRule()

    private lateinit var repository: NewsRepository
    private lateinit var viewmodel: NewsViewModel

    @Before fun setUp() {
        repository = mockk()
//        viewmodel = NewsViewModel(repository)
    }

    // TODO [T3.1] Skenario sukses: repository mengembalikan 2 artikel ->
    //  isLoading false, articles.size == 2, errorMessage null,
    //  dan getTopHeadlines(NewsCategory.General) dipanggil tepat 1 kali.
    @Test fun `sukses - headline mengisi daftar artikel`() = runTest {
        val Fake = NewsFeed(listOf(
            Article("x","x","x","x","x","x","x","x"),
            Article("x","x","x","x","x","x","x","x")
        ))
        coEvery {
            repository.getTopHeadlines(any())
        } returns Result.success(Fake)
        viewmodel = NewsViewModel(repository)
        val state = viewmodel.uiState.value

        assertFalse(state.isLoading)
        assertEquals(2, state.articles.size)
        assertNull(state.errorMessage)
    }

    // TODO [T3.2] Skenario gagal: repository mengembalikan Result.failure(IOException()) ->
    //  articles kosong dan errorMessage == "Tidak ada koneksi internet."
    @Test fun `gagal - pesan error tampil dan daftar kosong`() = runTest {
        coEvery {
            repository.getTopHeadlines(any())
        } returns Result.failure(IOException())
        viewmodel = NewsViewModel(repository)
        viewmodel.refresh()
        advanceUntilIdle()
        val state = viewmodel.uiState.value

        assertFalse(state.isLoading)
        assertTrue(state.articles.isEmpty())
        assertEquals("Tidak ada koneksi internet.", state.errorMessage)
    }
}
