package com.diavolo.mynewz.ui.newsSource

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diavolo.mynewz.data.model.Source
import com.diavolo.mynewz.databinding.ItemNewsCategoryBinding
import com.diavolo.mynewz.databinding.ItemNewsSourceBinding

/**
 * Written with passion by Ikhsan Hidayat on 08/08/2023.
 */
class NewsSourceAdapter(private val itemClick: (Source) -> Unit) :
    RecyclerView.Adapter<NewsSourceAdapter.NewsSourceViewHolder>() {

    private var items: MutableList<Source> = mutableListOf()

    fun setItems(items: List<Source>) {
        clearItems()
        addItems(items)
    }

    fun addItems(items: List<Source>) {
        this.items.addAll(items)
    }

    fun clearItems() {
        this.items.clear()
    }

    class NewsSourceViewHolder(private val binding: ItemNewsSourceBinding, val itemClick: (Source) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Source) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.tvNameSource.text = name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsSourceViewHolder {
        val binding = ItemNewsSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsSourceAdapter.NewsSourceViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewsSourceViewHolder, position: Int) {
        holder.bindView(items[position])
    }
}