package com.alurwa.animerisuto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alurwa.animerisuto.databinding.ListItemAnimeRankingBinding
import com.alurwa.animerisuto.model.Anime

/**
 * Created by Purwa Shadr Al 'urwa on 18/07/2021
 */

class AnimeRankingAdapter(
    private val onItemClickCallback: (id: Int) -> Unit
) : PagingDataAdapter<Anime, AnimeRankingAdapter.ViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemAnimeRankingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ViewHolder(
        private val binding: ListItemAnimeRankingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = getItem(position)

            with(binding) {
                anime = item
                txtRank.text = (bindingAdapterPosition + 1).toString()
                root.setOnClickListener {
                    if (item != null) {
                        onItemClickCallback.invoke(item.id)
                    }
                }
                executePendingBindings()
            }
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Anime>() {
            override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean =
                oldItem.id == newItem.id
        }
    }
}
