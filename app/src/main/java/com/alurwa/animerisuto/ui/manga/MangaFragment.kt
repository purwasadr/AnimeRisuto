package com.alurwa.animerisuto.ui.manga

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
import com.alurwa.animerisuto.databinding.FragmentMangaBinding
import com.alurwa.animerisuto.utils.SpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MangaFragment : Fragment() {

    private var _binding: FragmentMangaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter by lazy {
        AnimeAdapter()
    }

    private val viewModel: MangaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMangaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        setupRecyclerView()

        setupSwipeToRefresh()

        getManga()
    }

    private fun getManga() {
        lifecycleScope.launch {
            viewModel.mangaPaging.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupAdapter() {
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.swipeRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collect { loadStates ->
                //   Timber.d("Refresh tok " + loadStates.refresh.toString())
                //   Timber.d("Source Refresh " + loadStates.source.refresh.toString())
                //  Timber.d(loadStates.toString())
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow

                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    Timber.d("Refresh tok " + it.refresh.toString())
                    Timber.d("Source Refresh " + it.source.refresh.toString())
                    binding.listManga.scrollToPosition(0)
                }
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }

    private fun setupRecyclerView() {
        binding.listManga.setHasFixedSize(true)
        binding.listManga.layoutManager = LinearLayoutManager(requireContext())
        binding.listManga.addItemDecoration(
            SpacingDecoration(
                16,
                16,
                RecyclerView.VERTICAL
            )
        )
        binding.listManga.adapter = adapter.withLoadStateHeaderAndFooter(
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
