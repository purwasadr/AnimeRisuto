package com.alurwa.animerisuto.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alurwa.animerisuto.data.IAnimeRepository
import com.alurwa.animerisuto.model.Anime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Purwa Shadr Al 'urwa on 28/06/2021
 */

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: IAnimeRepository
) : ViewModel() {

    private var currentQuery: String? = null
    private var currentSearchResult: Flow<PagingData<Anime>>? = null

    fun searchAnime(query: String): Flow<PagingData<Anime>> {
        val lastResult = currentSearchResult
        if (query == currentQuery && lastResult != null) {
            return lastResult
        }

        currentQuery = query

        val newResult = repository.getAnimeSearch(query).cachedIn(viewModelScope)

        currentSearchResult = newResult

        return newResult
    }
}
