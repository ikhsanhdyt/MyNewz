package com.diavolo.mynewz.ui.newsCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diavolo.mynewz.data.repository.NewsCategoryRepository
import com.diavolo.mynewz.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * Written with passion by Ikhsan Hidayat on 07/08/2023.
 */
class NewsCategoryViewModel(private val newsCategoryRepository: NewsCategoryRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<String>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<String>>> = _uiState

    init {
        fetchNewsCategories()
    }

    private fun fetchNewsCategories() {
        viewModelScope.launch {
            newsCategoryRepository.fetchNewsCategory()
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}