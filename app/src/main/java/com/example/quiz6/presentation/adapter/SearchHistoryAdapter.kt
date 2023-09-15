package com.example.quiz6.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz6.databinding.ItemSearchBinding

class SearchHistoryAdapter(private val itemClickListener: (String) -> Unit) : ListAdapter<String, SearchHistoryAdapter.ViewHolder>(SearchHistoryDiffCallback()) {
    private var selectedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(searchResult: String) {
            binding.tvSearchResult.isSelected = adapterPosition == selectedPosition // 依照選中位置來設定 isSelected 屬性

            binding.tvSearchResult.text = searchResult
            itemView.setOnClickListener{
                selectedPosition = adapterPosition
                notifyDataSetChanged()
                itemClickListener(searchResult)
            }
        }
    }
}
class SearchHistoryDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
