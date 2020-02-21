package com.rajith.codetest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rajith.codetest.databinding.ItemFooterBinding
import com.rajith.codetest.databinding.ItemHeadlineBinding
import com.rajith.codetest.model.News
import com.rajith.codetest.util.UiState

class NewsAdapter(private val listener: NewsClickListener) : PagedListAdapter<News, RecyclerView.ViewHolder>(
    diffCallback
) {

    private val FOOTER_TYPE = 1
    private val HEADLINE_TYPE = 2
    private var state : UiState = UiState.Loading

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADLINE_TYPE) HeadlineViewHolder.create(parent) else FooterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == HEADLINE_TYPE) (holder as HeadlineViewHolder).bind(getItem(position) ?: News("","","","","","", "","","",""), listener) else  (holder as FooterViewHolder).bind()
    }

    override fun getItemViewType(position: Int): Int {
        return if(position < super.getItemCount()) HEADLINE_TYPE else FOOTER_TYPE
    }


    class FooterViewHolder(private val binding: ItemFooterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): FooterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFooterBinding.inflate(layoutInflater, parent,false)
                return FooterViewHolder(binding)
            }
        }
    }

    class HeadlineViewHolder(private val binding: ItemHeadlineBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(headline: News, listener: NewsClickListener) {
            binding.headline = headline
            binding.root.setOnClickListener { listener.onHeadlinePressed(headline) }
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): HeadlineViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHeadlineBinding.inflate(layoutInflater, parent,false)
                return HeadlineViewHolder(binding)
            }
        }
    }

    interface NewsClickListener {
        fun onHeadlinePressed(headline : News)
    }


    private fun hasFooter() : Boolean {
        return super.getItemCount() != 0 && (state == UiState.Loading)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    fun setState(state : UiState) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }

        }
    }
}