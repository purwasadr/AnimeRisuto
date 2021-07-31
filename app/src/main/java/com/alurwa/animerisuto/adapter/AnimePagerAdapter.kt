package com.alurwa.animerisuto.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alurwa.animerisuto.ui.anime.AnimeRankingFragment
import com.alurwa.animerisuto.ui.anime.AnimeSeasonalFragment
import com.alurwa.animerisuto.ui.anime.AnimeSuggestionFragment

/**
 * Created by Purwa Shadr Al 'urwa on 16/07/2021
 */

class AnimePagerAdapter(
    fm: Fragment
) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> AnimeSuggestionFragment()
            1 -> AnimeRankingFragment()
            2 -> AnimeSeasonalFragment()
            else -> throw IllegalStateException("Out of index")
        }
}
