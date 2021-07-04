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
import androidx.recyclerview.widget.RecyclerView
import com.alurwa.animerisuto.adapter.AnimeAdapter
import com.alurwa.animerisuto.adapter.AnimeLoadStateAdapter
import com.alurwa.animerisuto.databinding.FragmentAnimeBinding
import com.alurwa.animerisuto.ui.animedetail.AnimeDetailActivity
import com.alurwa.animerisuto.utils.SpacingDecoration
import com.alurwa.animerisuto.utils.asMergedLoadStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import timber.log.Timber

@AndroidEntryPoint
class AnimeFragment : Fragment() {

    private var _binding: FragmentAnimeBinding? = null

    private val binding get() = _binding!!

    private val viewModel: AnimeViewModel by viewModels()

    private val adapter by lazy {
        AnimeAdapter() { id -> navigateToAnimeDetail(id) }
    }

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
        setupAdapter()

        setupRecyclerView()

        setupSwipeToRefresh()

        getAnime()
    }

    private fun getAnime() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getAnimeList.collectLatest {
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
                .asMergedLoadStates()
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    Timber.d("Refresh tok " + it.refresh.toString())
                    binding.rcvAnime.scrollToPosition(0)
                }
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }

    private fun setupRecyclerView() {
        binding.rcvAnime.setHasFixedSize(true)
        binding.rcvAnime.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvAnime.addItemDecoration(
            SpacingDecoration(
                16,
                16,
                RecyclerView.VERTICAL
            )
        )
        binding.rcvAnime.adapter = adapter.withLoadStateHeaderAndFooter(
            header = AnimeLoadStateAdapter(),
            footer = AnimeLoadStateAdapter() {
                adapter.retry()
            }
        )
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
