package com.diavolo.mynewz.ui.newsDetailWebView

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.diavolo.mynewz.MyNewzApplication
import com.diavolo.mynewz.databinding.ActivityNewsDetailWebViewBinding
import com.diavolo.mynewz.di.component.DaggerActivityComponent
import com.diavolo.mynewz.di.module.ActivityModule
import com.diavolo.mynewz.ui.newsArticle.NewsArticleActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class NewsDetailWebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailWebViewBinding

    @Inject
    lateinit var newsDetailWebViewModel: NewsDetailWebViewModel

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        loadUrl()
    }

    private fun loadUrl() = runBlocking {
        launch {
            newsDetailWebViewModel.intentExtrasFlow.onEach { bundle ->
                val url = bundle?.getString(EXTRAS_URL_DATA)?.lowercase()
                binding.webview.loadUrl(url ?: "")
            }.launchIn(lifecycleScope)
        }
    }

    private fun getIntentData() {
        newsDetailWebViewModel.setIntentData(intent)
    }

    override fun onBackPressed() {
        if (binding.webview.canGoBack()) binding.webview.goBack()
        else super.onBackPressed()
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as MyNewzApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)

    }

    companion object {
        private const val EXTRAS_URL_DATA = "EXTRAS_URL_DATA"

        @JvmStatic
        fun startActivity(context: Context?, url: String) {
            val intent = Intent(context, NewsDetailWebViewActivity::class.java)
            intent.putExtras(bundleOf().apply {
                putString(EXTRAS_URL_DATA, url)
            })
            context?.startActivity(intent)
        }
    }
}
