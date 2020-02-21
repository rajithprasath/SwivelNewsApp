package com.rajith.codetest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rajith.codetest.R
import com.rajith.codetest.databinding.ItemNewsCategoryBinding
import com.rajith.codetest.model.NewsCategory


class NewsCategoryAdapter (private val listener: NewsCategoryClickListener) : ListAdapter<NewsCategory, NewsCategoryAdapter.NewsChannelViewHolder>(
    diffCallback
)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsChannelViewHolder {
        return NewsChannelViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NewsChannelViewHolder, position: Int) {
        holder.bind(getItem(position) ?: NewsCategory(position,"",false), listener)
    }

    class NewsChannelViewHolder(private val binding: ItemNewsCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: NewsCategory, listener: NewsCategoryClickListener) {
           if(category.isSelected){
               binding.imageView.setBackgroundResource(R.drawable.category_bg_02)
           }else{
               binding.imageView.setBackgroundResource(R.drawable.category_bg_01)
           }
            binding.category = category
            binding.root.setOnClickListener {
                listener.onNewsCategoryPressed(category)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): NewsChannelViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsCategoryBinding.inflate(layoutInflater, parent,false)
                return NewsChannelViewHolder(binding)
            }
        }
    }


    interface NewsCategoryClickListener {
        fun onNewsCategoryPressed(category: NewsCategory)
    }


    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<NewsCategory>() {
            override fun areItemsTheSame(oldItem: NewsCategory, newItem: NewsCategory): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NewsCategory, newItem: NewsCategory): Boolean {
                return oldItem == newItem
            }

        }
    }
}