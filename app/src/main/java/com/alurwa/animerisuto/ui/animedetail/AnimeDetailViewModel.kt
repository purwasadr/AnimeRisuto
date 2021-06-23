package com.alurwa.animerisuto.ui.animedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alurwa.animerisuto.data.IAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Purwa Shadr Al 'urwa on 18/06/2021
 */

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val repository: IAnimeRepository,
    private val stateHandle: SavedStateHandle
) : ViewModel() {
    val extraId = stateHandle.get<Int>(AnimeDetailActivity.EXTRA_ID)!!

    val animeDetail = repository.getAnimeDetails(extraId).asLiveData()
}
