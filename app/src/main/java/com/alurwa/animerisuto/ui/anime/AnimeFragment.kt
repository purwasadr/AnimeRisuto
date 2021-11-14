package com.alurwa.animerisuto.ui.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alurwa.animerisuto.R
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

        // Panggil dahulu agar Child Fragment dapat menggunakan Shared ViewModel
        viewModel

        setupViewPager()
        setupTabLayoutMediator()
    }

    private fun setupViewPager() {
        binding.vp.adapter = AnimePagerAdapter(this)
        binding.vp.offscreenPageLimit = 1
    }

    private fun setupTabLayoutMediator() {
        TabLayoutMediator(
            binding.tabLayout,
            binding.vp,
            true
        ) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_item_suggestion)
                1 -> tab.text = getString(R.string.tab_item_ranking)
                2 -> tab.text = getString(R.string.tab_item_seasonal)
                else -> throw IllegalStateException()
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
