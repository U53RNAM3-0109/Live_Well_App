package com.example.livewellrestructured.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livewellrestructured.network.LiveWellApi
import com.example.livewellrestructured.network.LiveWellAppData
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface LiveWellUiState {
    data class Success (val appData: LiveWellAppData) : LiveWellUiState
    object Error : LiveWellUiState
    object Loading : LiveWellUiState
}

class LiveWellViewModel : ViewModel() {
    var liveWellUiState: LiveWellUiState by mutableStateOf(LiveWellUiState.Loading)
    private set
    init {
        getLiveWellData()
    }
    fun getLiveWellData() {
        viewModelScope.launch {
            liveWellUiState = try {
                val listResult = LiveWellApi.retrofitService.getLiveWellData()
                LiveWellUiState.Success(listResult)
            } catch (e: IOException) {
                LiveWellUiState.Error
            }
        }
    }
}