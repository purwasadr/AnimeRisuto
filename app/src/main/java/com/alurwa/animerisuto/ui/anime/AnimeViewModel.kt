package com.alurwa.animerisuto.ui.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alurwa.animerisuto.data.AnimeRepository
import com.alurwa.animerisuto.model.Anime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {
    val getAnimeList = repository.getAnimeSuggestions().cachedIn(viewModelScope)

    private var currentTypeValue: String? = null

    private var currentAnimeResult: Flow<PagingData<Anime>>? = null

    fun animeRanking(type: String): Flow<PagingData<Anime>> {
        val lastResult = currentAnimeResult
        if (type == currentTypeValue && lastResult != null) {
            return lastResult
        }

        currentTypeValue = type

        val newResult = repository.getAnimeRankingPaging(type).cachedIn(viewModelScope)

        currentAnimeResult = newResult

        return newResult
    }
}
