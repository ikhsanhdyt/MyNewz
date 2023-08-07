package com.diavolo.mynewz.ui.base

/**
 * Written with passion by Ikhsan Hidayat on 07/08/2023.
 */

sealed interface UiState<out T> {

    data class Success<T>(val data: T) : UiState<T>

    data class Error(val message: String) : UiState<Nothing>

    object Loading : UiState<Nothing>
}