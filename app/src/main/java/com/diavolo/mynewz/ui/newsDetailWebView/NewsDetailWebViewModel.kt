package com.diavolo.mynewz.ui.newsDetailWebView

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Written with passion by Ikhsan Hidayat on 08/08/2023.
 */
class NewsDetailWebViewModel @Inject constructor():ViewModel() {
    private val _intentExtrasFlow = MutableStateFlow<Bundle?>(null)

    val intentExtrasFlow: StateFlow<Bundle?>
        get() = _intentExtrasFlow.asStateFlow()

    fun setIntentData(intent: Intent) {
        // Assign the bundle from the intent extras to the MutableStateFlow
        _intentExtrasFlow.value = intent.extras

    }
}