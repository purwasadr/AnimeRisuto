package com.alurwa.animerisuto.ui.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alurwa.animerisuto.databinding.FragmentAnimeSeasonalBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Purwa Shadr Al 'urwa on 17/07/2021
 */

@AndroidEntryPoint
class AnimeSeasonalFragment : Fragment() {
    private var _binding: FragmentAnimeSeasonalBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeSeasonalBinding.inflate(inflater, container, false)
        return binding.root
    }
}
