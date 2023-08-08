package com.diavolo.mynewz.ui.newsSource

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diavolo.mynewz.data.model.Source
import com.diavolo.mynewz.data.repository.NewsSourceRepository
import com.diavolo.mynewz.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * Written with passion by Ikhsan Hidayat on 08/08/2023.
 */
class NewsSourceViewModel(private val repository: NewsSourceRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Source>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Source>>> = _uiState

    private val _intentExtrasFlow = MutableStateFlow<Bundle?>(null)

    val intentExtrasFlow: StateFlow<Bundle?>
        get() = _intentExtrasFlow.asStateFlow()

    fun fetchSources(category: String) {
        viewModelScope.launch {
            repository.getSources(category)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun setIntentData(intent: Intent) {
        // Assign the bundle from the intent extras to the MutableStateFlow
        _intentExtrasFlow.value = intent.extras

    }
}