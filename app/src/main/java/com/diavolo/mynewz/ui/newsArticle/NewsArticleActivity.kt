package com.diavolo.mynewz.ui.newsArticle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.diavolo.mynewz.MyNewzApplication
import com.diavolo.mynewz.data.model.Article
import com.diavolo.mynewz.databinding.ActivityNewsArticleBinding
import com.diavolo.mynewz.di.component.DaggerActivityComponent
import com.diavolo.mynewz.di.module.ActivityModule
import com.diavolo.mynewz.ui.base.UiState
import com.diavolo.mynewz.ui.newsDetailWebView.NewsDetailWebViewActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsArticleActivity : AppCompatActivity() {

    @Inject
    lateinit var newsArticleViewModel: NewsArticleViewModel

    lateinit var adapter: NewsArticleAdapter

    private lateinit var binding: ActivityNewsArticleBinding

    private fun initView() {
        getIntentData()
        setupUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityNewsArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsArticleViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
//                            binding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            binding.rvNewsArticle.visibility = View.VISIBLE
                        }

                        is UiState.Loading -> {
//                            binding.progressBar.visibility = View.VISIBLE
                            binding.rvNewsArticle.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            //Handle Error
//                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@NewsArticleActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(data: List<Article>) {
        data.let {
            adapter.addItems(it)
            adapter.notifyDataSetChanged()
        }

    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as MyNewzApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)

    }

    private fun setupUI() {
        adapter = NewsArticleAdapter {
            NewsDetailWebViewActivity.startActivity(this, it.url)
        }

        val recyclerView = binding.rvNewsArticle
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun getIntentData() {
        newsArticleViewModel.setIntentData(intent)
        newsArticleViewModel.intentExtrasFlow.onEach { bundle ->
            val source = bundle?.getString(EXTRAS_SOURCE_DATA)?.lowercase()
            newsArticleViewModel.fetchArticle(source ?: "")
        }.launchIn(lifecycleScope)
    }

    companion object {
        private const val EXTRAS_SOURCE_DATA = "EXTRAS_SOURCE_DATA"

        @JvmStatic
        fun startActivity(context: Context?, category: String) {
            val intent = Intent(context, NewsArticleActivity::class.java)
            intent.putExtras(bundleOf().apply {
                putString(EXTRAS_SOURCE_DATA, category)
            })
            context?.startActivity(intent)
        }
    }
}