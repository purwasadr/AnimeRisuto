package com.alurwa.animerisuto.ui.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alurwa.animerisuto.adapter.AnimePagerAdapter
import com.alurwa.animerisuto.databinding.FragmentAnimeBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeFragment : Fragment() {

    private var _binding: FragmentAnimeBinding? = null

    private val binding get() = _binding!!

    private val viewModel: AnimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel

        setupViewPager()
        setupTabLayoutMediator()
    }

    private fun setupViewPager() {
        binding.vp.adapter = AnimePagerAdapter(this)
        binding.vp.offscreenPageLimit = 1
        binding.vp.isUserInputEnabled = false
    }

    private fun setupTabLayoutMediator() {
        TabLayoutMediator(
            binding.tabLayout,
            binding.vp,
            true,
            false
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "Suggestion"
                1 -> tab.text = "Ranking"
                2 -> tab.text = "Seasonal"
                else -> throw IllegalStateException()
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
