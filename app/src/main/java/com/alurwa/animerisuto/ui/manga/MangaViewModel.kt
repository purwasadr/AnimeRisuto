package com.alurwa.animerisuto.ui.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alurwa.animerisuto.data.repository.manga.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    repository: MangaRepository
) : ViewModel() {
    val mangaPaging = repository.getMangaPaging().cachedIn(viewModelScope)
}
