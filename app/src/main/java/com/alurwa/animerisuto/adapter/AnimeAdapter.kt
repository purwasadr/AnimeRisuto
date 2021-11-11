package com.alurwa.animerisuto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alurwa.animerisuto.databinding.RcvItemAnimeBinding
import com.alurwa.animerisuto.model.Anime

class AnimeAdapter(
    private val onItemClickCallback: (id: Int) -> Unit
) : PagingDataAdapter<Anime, AnimeAdapter.ViewHolder>(COMPARATOR) {

    inner class ViewHolder(
        val binding: RcvItemAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = getItem(position)

            with(binding) {
                anime = item
                root.setOnClickListener {
                    if (item != null) {
                        onItemClickCallback.invoke(item.id)
                    }
                }
                binding.executePendingBindings()
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
        val COMPARATOR = object : DiffUtil.ItemCallback<Anime>() {
            override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean =
                oldItem.id == newItem.id
        }
    }
}
