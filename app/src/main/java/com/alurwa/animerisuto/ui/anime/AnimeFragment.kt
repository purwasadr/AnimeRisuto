package com.alurwa.animerisuto.ui.anime

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
import com.alurwa.animerisuto.utils.SpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import timber.log.Timber

@AndroidEntryPoint
class AnimeFragment : Fragment() {

    private var _binding: FragmentAnimeBinding? = null

    private val binding get() = _binding!!

    private val viewModel: AnimeViewModel by viewModels()

    private val adapter by lazy {
        AnimeAdapter()
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
            adapter.loadStateFlow.collect { loadStates ->
                //   Timber.d("Refresh tok " + loadStates.refresh.toString())
                //   Timber.d("Source Refresh " + loadStates.source.refresh.toString())
                //  Timber.d(loadStates.toString())
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow

                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChanged { old, new ->
                    old.mediator?.prepend?.endOfPaginationReached ==
                        new.mediator?.prepend?.endOfPaginationReached
                }
            /*    .distinctUntilChanged { old, new ->
                    old.mediator?.refresh == new.mediator?.refresh && old.source.refresh != new.source.refresh
                }

             */
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    Timber.d("Refresh tok " + it.refresh.toString())
                    Timber.d("Source Refresh " + it.source.refresh.toString())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
