package com.diavolo.mynewz.ui.newsCategory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.diavolo.mynewz.MyNewzApplication
import com.diavolo.mynewz.databinding.ActivityNewsCategoryBinding
import com.diavolo.mynewz.di.component.DaggerActivityComponent
import com.diavolo.mynewz.di.module.ActivityModule
import com.diavolo.mynewz.ui.base.UiState
import com.diavolo.mynewz.ui.newsSource.NewsSourceActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsCategoryActivity : AppCompatActivity() {

    @Inject
    lateinit var newsCategoryViewModel: NewsCategoryViewModel

    lateinit var adapter: NewsCategoryAdapter

    private lateinit var binding: ActivityNewsCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityNewsCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        adapter = NewsCategoryAdapter {
            NewsSourceActivity.startActivity(this, it)
        }

        val recyclerView = binding.rvNewsCategory
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsCategoryViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
//                            binding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            binding.rvNewsCategory.visibility = View.VISIBLE
                        }
                        is UiState.Loading -> {
//                            binding.progressBar.visibility = View.VISIBLE
                            binding.rvNewsCategory.visibility = View.GONE
                        }
                        is UiState.Error -> {
                            //Handle Error
//                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@NewsCategoryActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(data: List<String>) {
        data.let {
            adapter.clearItems()
            adapter.addItems(it)
            adapter.notifyDataSetChanged()
        }

    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as MyNewzApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}