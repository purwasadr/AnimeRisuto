package com.alurwa.animerisuto.ui.search

import androidx.lifecycle.ViewModel
import com.alurwa.animerisuto.data.IAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Purwa Shadr Al 'urwa on 28/06/2021
 */

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: IAnimeRepository
) : ViewModel() {

}