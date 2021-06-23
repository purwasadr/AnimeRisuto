package com.alurwa.animerisuto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alurwa.animerisuto.databinding.ListItemRecommendationsBinding
import com.alurwa.animerisuto.model.AnimeRecommendation

/**
 * Created by Purwa Shadr Al 'urwa on 21/06/2021
 */

class RecommendationAdapter(
    private val animeList: List<AnimeRecommendation>
) : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ListItemRecommendationsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = animeList.size

    inner class ViewHolder(
        private val binding: ListItemRecommendationsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val anime = animeList[position]

            binding.animeRecommendation = anime
        }
    }
}
