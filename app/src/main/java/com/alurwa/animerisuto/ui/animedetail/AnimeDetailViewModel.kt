package com.alurwa.animerisuto.ui.animedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alurwa.animerisuto.data.Resource
import com.alurwa.animerisuto.data.repository.animedetail.AnimeDetailRepository
import com.alurwa.animerisuto.model.AnimeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Purwa Shadr Al 'urwa on 18/06/2021
 */

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val repository: AnimeDetailRepository,
    private val stateHandle: SavedStateHandle
) : ViewModel() {
    val extraId = stateHandle.get<Int>(AnimeDetailActivity.EXTRA_ID)!!

    private var animeDetailJob: Job? = null

    private var _animeDetail3: MutableStateFlow<Resource<AnimeDetail?>> =
        MutableStateFlow(Resource.Loading())

    val animeDetail3: StateFlow<Resource<AnimeDetail?>> = _animeDetail3

    init {
        animeDetailJob = viewModelScope.launch {
            repository.getAnimeDetail(extraId).collectLatest {
                _animeDetail3.value = it
            }
        }
    }

    val animeDetail = repository.getAnimeDetail(extraId).asLiveData()
}
