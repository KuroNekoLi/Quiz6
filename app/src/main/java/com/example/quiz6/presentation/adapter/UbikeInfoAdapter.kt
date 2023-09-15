package com.example.quiz6.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz6.data.model.UbikeInfoItem
import com.example.quiz6.databinding.ItemUbikeBinding


class UbikeInfoAdapter : ListAdapter<UbikeInfoItem, UbikeInfoAdapter.ViewHolder>(UbikeInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUbikeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemUbikeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UbikeInfoItem) {
            binding.tvCity.text = "台北市"
            binding.tvArea.text = item.sarea
            binding.tvPoint.text = item.sna
        }
    }
}

class UbikeInfoDiffCallback : DiffUtil.ItemCallback<UbikeInfoItem>() {
    override fun areItemsTheSame(oldItem: UbikeInfoItem, newItem: UbikeInfoItem): Boolean {
        return oldItem.sna == newItem.sna
    }

    override fun areContentsTheSame(oldItem: UbikeInfoItem, newItem: UbikeInfoItem): Boolean {
        return oldItem.sna == newItem.sna
    }
}
