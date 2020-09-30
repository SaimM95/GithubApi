package com.example.scotiatakehomeassignment.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.scotiatakehomeassignment.databinding.ViewSearchResultItemBinding
import com.example.scotiatakehomeassignment.model.Repository

val diffCallback = object :
    DiffUtil.ItemCallback<Repository>() {
    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }

}

class SearchResultAdapter
    : ListAdapter<Repository, SearchResultAdapter.SearchResultViewHolder>(diffCallback) {

    var onItemClickedListener: ((Repository) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = ViewSearchResultItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultViewHolder(binding, binding.root)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val repository = getItem(position)
        holder.onBind(repository)
    }

    inner class SearchResultViewHolder(
        private val binding: ViewSearchResultItemBinding, itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val repository = getItem(adapterPosition)
                onItemClickedListener?.invoke(repository)
            }
        }

        fun onBind(repository: Repository) {
            binding.tvRepositoryName.text = repository.name
            binding.tvRepositoryDescription.text = repository.description
        }
    }
}