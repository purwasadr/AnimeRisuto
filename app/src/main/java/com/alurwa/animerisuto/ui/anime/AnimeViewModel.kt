package com.alurwa.animerisuto.ui.anime

import androidx.lifecycle.ViewModel
import com.alurwa.animerisuto.data.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {
    val getAnimeList = repository.getAnimeList()
}
