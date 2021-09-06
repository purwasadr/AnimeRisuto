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
import com.alurwa.animerisuto.R
import com.alurwa.animerisuto.adapter.AnimeAdapter
import com.alurwa.animerisuto.adapter.AnimeLoadStateAdapter
import com.alurwa.animerisuto.databinding.FragmentAnimeSeasonalBinding
import com.alurwa.animerisuto.model.SeasonalParam
import com.alurwa.animerisuto.ui.animedetail.AnimeDetailActivity
import com.alurwa.animerisuto.utils.SeasonType
import com.alurwa.animerisuto.utils.asMergedLoadStates
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by Purwa Shadr Al 'urwa on 17/07/2021
 */

@AndroidEntryPoint
class AnimeSeasonalFragment : Fragment() {
    private var _binding: FragmentAnimeSeasonalBinding? = null

    private val binding get() = _binding!!

    private val adapter by lazy {
        AnimeAdapter() { id -> navigateToAnimeDetail(id) }
    }

    private val viewModel: AnimeViewModel by viewModels({ requireParentFragment() })

    private var animeJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeSeasonalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupList()
        setupSwipeToRefresh()
        setupChip()
    }

    private fun getAnimeSeasonal(type: String, year: Int) {
        animeJob?.cancel()
        animeJob = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getAnimeSeasonal(type, year).collectLatest {
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
                    binding.listAnime.scrollToPosition(0)
                }
        }
    }

    private fun setupList() {
        binding.listAnime.setHasFixedSize(true)
        binding.listAnime.layoutManager = LinearLayoutManager(requireContext())
        binding.listAnime.adapter = adapter.withLoadStateFooter(
            footer = AnimeLoadStateAdapter() {
                adapter.retry()
            }
        )
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }

    private fun setupChip() {
        val seasonState = viewModel.chipSeasonState.value

        setChipChecked(seasonState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.chipYearState.collectLatest {
                binding.cpYear.text = it.toString()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.chipSeasonState
                .combine(viewModel.chipYearState) { t1, t2 ->
                    SeasonalParam(t1.code, t2)
                }
                .collectLatest {
                    getAnimeSeasonal(it.type, it.year)
                }
        }

        binding.cpGroup.setOnCheckedChangeListener { group, checkedId ->
            viewModel.setChipSeasonalState(idToSeasonType(checkedId))
        }

        binding.cpYear.setOnClickListener {
            dialogYear()
        }
    }

    private fun dialogYear() {

        val yearState = viewModel.chipYearState.value
        val rangeYear = (2021 downTo 2000)
        val arrayYear = Array(rangeYear.count()) {
            rangeYear.elementAt(it).toString()
        }
        val checkedItem = rangeYear.indexOf(yearState)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Choose year")
            .setSingleChoiceItems(arrayYear, checkedItem) { dialog, which ->
                viewModel.setChipYearState(arrayYear[which].toInt())
                dialog.dismiss()
            }
            .show()
    }

    private fun setChipChecked(type: SeasonType) {
        binding.cpGroup.run {
            when (type) {
                SeasonType.WINTER -> {
                    check(R.id.cp_winter)
                }
                SeasonType.SPRING -> check(R.id.cp_spring)
                SeasonType.SUMMER -> check(R.id.cp_summer)
                SeasonType.FALL -> check(R.id.cp_fall)
            }
        }

    }

    private fun idToSeasonType(id: Int) =
        binding.cpGroup.run {
            when (id) {
                R.id.cp_winter -> SeasonType.WINTER
                R.id.cp_spring -> SeasonType.SPRING
                R.id.cp_summer -> SeasonType.SUMMER
                R.id.cp_fall -> SeasonType.FALL
                else -> throw IllegalStateException()
            }
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
