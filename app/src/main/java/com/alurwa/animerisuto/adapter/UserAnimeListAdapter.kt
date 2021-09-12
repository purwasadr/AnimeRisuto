package com.alurwa.animerisuto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alurwa.animerisuto.databinding.ListItemUserAnimeListBinding
import com.alurwa.animerisuto.model.UserAnimeList

class UserAnimeListAdapter :
    PagingDataAdapter<UserAnimeList, UserAnimeListAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ListItemUserAnimeListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(
        private val binding: ListItemUserAnimeListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.userAnimeList = getItem(position)
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<UserAnimeList>() {
            override fun areContentsTheSame(
                oldItem: UserAnimeList,
                newItem: UserAnimeList
            ): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: UserAnimeList, newItem: UserAnimeList): Boolean =
                oldItem.id == newItem.id
        }
    }


}