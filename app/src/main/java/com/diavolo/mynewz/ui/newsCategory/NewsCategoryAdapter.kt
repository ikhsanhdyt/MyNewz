package com.diavolo.mynewz.ui.newsCategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diavolo.mynewz.databinding.ItemNewsCategoryBinding

/**
 * Written with passion by Ikhsan Hidayat on 07/08/2023.
 */
class NewsCategoryAdapter(private val categoryList: ArrayList<String>) :
    RecyclerView.Adapter<NewsCategoryAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemNewsCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            binding.tvName.text = category
            itemView.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            ItemNewsCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(categoryList[position])

    fun addData(list: List<String>) {
        categoryList.addAll(list)
    }
}