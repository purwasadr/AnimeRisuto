package com.alurwa.animerisuto.ui.addedituseranime

import androidx.lifecycle.ViewModel
import com.alurwa.animerisuto.data.repository.useranimelist.UserAnimeListRepository
import com.alurwa.animerisuto.model.UserAnimeListParam
import com.alurwa.animerisuto.utils.StatusType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddEditUserAnimeViewModel @Inject constructor(
    private val userAnimeListRepository: UserAnimeListRepository
) : ViewModel() {

    private val _status = MutableStateFlow(StatusType.WATCHING)
    val status = _status.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    fun setStatus(statusType: StatusType) {
        _status.value = statusType
    }

    fun setScore(score: Int) {
        _score.value = score
    }

    fun updateUserAnimeList(userAnimeListParam: UserAnimeListParam) =
        userAnimeListRepository.updateUserAnimeList(userAnimeListParam)
}