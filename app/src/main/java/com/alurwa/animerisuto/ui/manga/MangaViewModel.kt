package com.alurwa.animerisuto.ui.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alurwa.animerisuto.data.IAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    repository: IAnimeRepository
) : ViewModel() {
    val mangaPaging = repository.getMangaPaging().cachedIn(viewModelScope)
}
