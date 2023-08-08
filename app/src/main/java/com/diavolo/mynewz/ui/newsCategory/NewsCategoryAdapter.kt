package com.diavolo.mynewz.ui.newsCategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diavolo.mynewz.databinding.ItemNewsCategoryBinding

/**
 * Written with passion by Ikhsan Hidayat on 07/08/2023.
 */
class NewsCategoryAdapter(private val itemClick: (String) -> Unit) :
    RecyclerView.Adapter<NewsCategoryAdapter.NewsCategoryViewHolder>() {

    private var items: MutableList<String> = mutableListOf()


    class NewsCategoryViewHolder(private val binding: ItemNewsCategoryBinding, val itemClick: (String) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: String) {
            with(item){
                binding.tvNameCategory.text = item
                itemView.setOnClickListener {
                    itemClick(this)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsCategoryViewHolder {
        val binding = ItemNewsCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsCategoryAdapter.NewsCategoryViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewsCategoryViewHolder, position: Int) =
        holder.bindView(items[position])

    fun setItems(items: List<String>) {
        clearItems()
        addItems(items)
    }

    fun addItems(items: List<String>) {
        this.items.addAll(items)
    }

    fun clearItems() {
        this.items.clear()
    }
}