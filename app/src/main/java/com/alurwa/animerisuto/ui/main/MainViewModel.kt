package com.alurwa.animerisuto.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alurwa.animerisuto.data.IAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Purwa Shadr Al 'urwa on 10/05/2021
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: IAnimeRepository
) : ViewModel() {
    val user = repository.getUser().asLiveData()
}
