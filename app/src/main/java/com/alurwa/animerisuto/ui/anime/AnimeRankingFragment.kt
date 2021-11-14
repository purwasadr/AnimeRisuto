package com.alurwa.animerisuto.ui.anime

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.alurwa.animerisuto.adapter.AnimeLoadStateAdapter
import com.alurwa.animerisuto.adapter.AnimeRankingAdapter
import com.alurwa.animerisuto.databinding.FragmentAnimeRankingBinding
import com.alurwa.animerisuto.ui.animedetail.AnimeDetailActivity
import com.alurwa.animerisuto.utils.AnimeRankingType
import com.alurwa.animerisuto.utils.asMergedLoadStates
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import timber.log.Timber

/**
 * Created by Purwa Shadr Al 'urwa on 17/07/2021
 */

@AndroidEntryPoint
class AnimeRankingFragment() : Fragment() {

    private var _binding: FragmentAnimeRankingBinding? = null

    private val binding get() = _binding!!

    private val viewModel: AnimeViewModel by viewModels({ requireParentFragment() })

    private val adapter by lazy {
        AnimeRankingAdapter { navigateToAnimeDetail(it) }
    }

    private var animeJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAnimeRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupChipGroup()
        setupRecyclerView()
        setupSwipeToRefresh()
    }

    private fun getAnimeRanking(type: String) {
        animeJob?.cancel()
        animeJob = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.animeRanking(type).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupAdapter() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.swipeRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                // Menggunakan LoadState yang diperuntukkan untuk RemoteMediator
                .asMergedLoadStates()
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    Timber.d("Refresh tok " + it.refresh.toString())
                    binding.listAnime.scrollToPosition(0)
                }
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }

    private fun setupRecyclerView() {
        binding.listAnime.setHasFixedSize(true)
        binding.listAnime.layoutManager = LinearLayoutManager(requireContext())
        binding.listAnime.adapter = adapter.withLoadStateFooter(
            footer = AnimeLoadStateAdapter() {
                adapter.retry()
            }
        )
    }

    private fun setupChipGroup() {
        val chipIds = mutableListOf<Int>()

        AnimeRankingType.values().forEachIndexed { index, animeRankingType ->
            val chip = Chip(requireContext())
                .also {
                    it.text = animeRankingType.code
                    it.isCheckable = true
                    it.id = View.generateViewId()

                    chipIds.add(it.id)
                }
            binding.cpGroup.addView(chip)
        }

        binding.cpGroup.setOnCheckedChangeListener { group, checkedId ->
            val index = chipIds.indexOf(checkedId)
            val type = AnimeRankingType.values()[index].code
            getAnimeRanking(type)
        }

        binding.cpGroup.check(chipIds[0])
    }

    private fun navigateToAnimeDetail(id: Int) {
        Intent(requireContext(), AnimeDetailActivity::class.java)
            .putExtra(AnimeDetailActivity.EXTRA_ID, id)
            .also {
                startActivity(it)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
