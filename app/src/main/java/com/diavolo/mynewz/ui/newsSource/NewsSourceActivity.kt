package com.diavolo.mynewz.ui.newsSource

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
import com.diavolo.mynewz.data.model.Source
import com.diavolo.mynewz.databinding.ActivityNewsSourceBinding
import com.diavolo.mynewz.di.component.DaggerActivityComponent
import com.diavolo.mynewz.di.module.ActivityModule
import com.diavolo.mynewz.ui.base.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsSourceActivity : AppCompatActivity() {

    @Inject
    lateinit var newsSourceViewModel: NewsSourceViewModel

    lateinit var adapter: NewsSourceAdapter

    private lateinit var binding: ActivityNewsSourceBinding

    private fun initView() {
        getIntentData()
        setupUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityNewsSourceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsSourceViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
//                            binding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            binding.rvNewsSource.visibility = View.VISIBLE
                        }

                        is UiState.Loading -> {
//                            binding.progressBar.visibility = View.VISIBLE
                            binding.rvNewsSource.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            //Handle Error
//                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@NewsSourceActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(data: List<Source>) {
        data.let {
            adapter.addItems(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupUI() {
        adapter = NewsSourceAdapter {

        }

        val recyclerView = binding.rvNewsSource
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as MyNewzApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)

    }

    private fun getIntentData() {
        newsSourceViewModel.setIntentData(intent)
        newsSourceViewModel.intentExtrasFlow.onEach { bundle ->
            val category = bundle?.getString(EXTRAS_CATEGORY_DATA)?.lowercase()
            newsSourceViewModel.fetchSources(category ?: "")
        }.launchIn(lifecycleScope)
    }

    companion object {
        private const val EXTRAS_CATEGORY_DATA = "EXTRAS_CATEGORY_DATA"

        @JvmStatic
        fun startActivity(context: Context?, category: String) {
            val intent = Intent(context, NewsSourceActivity::class.java)
            intent.putExtras(bundleOf().apply {
                putString(EXTRAS_CATEGORY_DATA, category)
            })
            context?.startActivity(intent)
        }
    }
}