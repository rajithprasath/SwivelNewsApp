package com.rajith.codetest.util

sealed class UiState {
    object Loading : UiState()
    object Success: UiState()
    object Complete : UiState()
    object Error: UiState()
}
