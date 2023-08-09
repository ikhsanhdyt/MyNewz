package com.diavolo.mynewz.ui.newsArticle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diavolo.mynewz.data.model.Article
import com.diavolo.mynewz.data.model.Source
import com.diavolo.mynewz.databinding.ItemNewsArticleBinding
import com.diavolo.mynewz.databinding.ItemNewsSourceBinding

/**
 * Written with passion by Ikhsan Hidayat on 08/08/2023.
 */
class NewsArticleAdapter(private val itemClick: (Article) -> Unit) :
    RecyclerView.Adapter<NewsArticleAdapter.NewsArticleViewHolder>() {
    private var items: MutableList<Article> = mutableListOf()

    fun setItems(items: List<Article>) {
        clearItems()
        addItems(items)
    }

    fun addItems(items: List<Article>) {
        this.items.addAll(items)
    }

    fun clearItems() {
        this.items.clear()
    }

    class NewsArticleViewHolder(
        private val binding: ItemNewsArticleBinding,
        val itemClick: (Article) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Article) {
            with(item) {
                itemView.setOnClickListener {
                    itemClick(this)
                }
                binding.tvNameArticle.text = title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleViewHolder {
        val binding =
            ItemNewsArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsArticleViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewsArticleViewHolder, position: Int) {
        holder.bindView(items[position])
    }
}