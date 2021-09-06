package com.alurwa.animerisuto.ui.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alurwa.animerisuto.data.repository.anime.AnimeRepository
import com.alurwa.animerisuto.model.Anime
import com.alurwa.animerisuto.utils.SeasonType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {
    val getAnimeList = repository.getAnimeSuggestions().cachedIn(viewModelScope)

    private var currentRankingTypeValue: String? = null

    private var currentRankingAnimeResult: Flow<PagingData<Anime>>? = null

    private var currentSeasonalYear: Int? = null

    private var currentSeasonalSeasonType: String? = null

    private var currentAnimeSeasonalResult: Flow<PagingData<Anime>>? = null

    private val _chipYearState = MutableStateFlow(2021)

    val chipYearState: StateFlow<Int> = _chipYearState

    private val _chipSeasonState = MutableStateFlow(SeasonType.WINTER)

    val chipSeasonState: StateFlow<SeasonType> = _chipSeasonState

    fun animeRanking(type: String): Flow<PagingData<Anime>> {
        val lastResult = currentRankingAnimeResult
        if (type == currentRankingTypeValue && lastResult != null) {
            return lastResult
        }

        currentRankingTypeValue = type

        val newResult = repository.getAnimeRankingPaging(type).cachedIn(viewModelScope)

        currentRankingAnimeResult = newResult

        return newResult
    }

    fun getAnimeSeasonal(type: String, year: Int): Flow<PagingData<Anime>> {
        val lastResult = currentAnimeSeasonalResult
        if (
            type == currentSeasonalSeasonType &&
            year == currentSeasonalYear  &&
            lastResult != null
        ) {
            return lastResult
        }

        currentSeasonalSeasonType = type
        currentSeasonalYear = year

        val newResult = repository
            .getAnimeSeasonalPaging(type, year)
            .cachedIn(viewModelScope)

        currentAnimeSeasonalResult = newResult

        return newResult
    }

    fun setChipSeasonalState(type: SeasonType) {
        _chipSeasonState.value = type
    }

    fun setChipYearState(value: Int) {
        _chipYearState.value = value
    }
}
