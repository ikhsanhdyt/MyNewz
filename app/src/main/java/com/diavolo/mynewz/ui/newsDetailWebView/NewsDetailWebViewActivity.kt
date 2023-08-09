package com.diavolo.mynewz.ui.newsDetailWebView

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.diavolo.mynewz.MyNewzApplication
import com.diavolo.mynewz.databinding.ActivityNewsDetailWebViewBinding
import com.diavolo.mynewz.di.component.DaggerActivityComponent
import com.diavolo.mynewz.di.module.ActivityModule
import com.diavolo.mynewz.utils.NetworkUtils
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class NewsDetailWebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailWebViewBinding

    @Inject
    lateinit var newsDetailWebViewModel: NewsDetailWebViewModel

    @Inject
    lateinit var networkUtils: NetworkUtils

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentData()
        binding.webview.webViewClient = AppWebViewClients()
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.loadWithOverviewMode = true
        binding.webview.settings.useWideViewPort = true
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

    private inner class AppWebViewClients :
        WebViewClient() {

        override fun onPageCommitVisible(view: WebView?, url: String?) {
            binding.progressBar.visibility = View.GONE
            super.onPageCommitVisible(view, url)
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            binding.clError.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView, url: String) {

            binding.progressBar.visibility = View.GONE
            super.onPageFinished(view, url)
            if (!networkUtils.isInternetAvailable()) {
                binding.clError.visibility = View.VISIBLE
            }
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
