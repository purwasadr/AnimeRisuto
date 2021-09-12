package com.alurwa.animerisuto.ui.useranimelist

import androidx.lifecycle.ViewModel
import com.alurwa.animerisuto.data.repository.useranimelist.UserAnimeListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserAnimeListViewModel @Inject constructor(
    private val repository: UserAnimeListRepository
) : ViewModel() {

    val userAnimeList = repository.getUserAnimeListPaging()

}