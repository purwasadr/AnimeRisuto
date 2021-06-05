package com.alurwa.animerisuto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alurwa.animerisuto.databinding.RcvItemAnimeBinding
import com.alurwa.animerisuto.model.Anime
import com.alurwa.animerisuto.model.Manga

/**
 * Created by Purwa Shadr Al 'urwa on 01/06/2021
 */

class MangaAdapter : PagingDataAdapter<Manga, MangaAdapter.ViewHolder>(COMPARATOR) {

    inner class ViewHolder(
        private val binding: RcvItemAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = getItem(position)

            item?.apply {
                binding.anime = Anime(id, title, posterPath, emptyList(), mean, item.no)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RcvItemAnimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Manga>() {
            override fun areItemsTheSame(oldItem: Manga, newItem: Manga): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Manga, newItem: Manga): Boolean =
                oldItem == newItem
        }
    }
}
