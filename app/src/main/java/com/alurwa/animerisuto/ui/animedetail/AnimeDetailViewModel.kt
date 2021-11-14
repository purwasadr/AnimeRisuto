package com.alurwa.animerisuto.ui.animedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alurwa.animerisuto.data.Resource
import com.alurwa.animerisuto.data.repository.animedetail.AnimeDetailRepository
import com.alurwa.animerisuto.data.repository.translate.TranslateRepository
import com.alurwa.animerisuto.model.AnimeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Purwa Shadr Al 'urwa on 18/06/2021
 */

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val repository: AnimeDetailRepository,
    private val translateRepository: TranslateRepository,
    private val stateHandle: SavedStateHandle
) : ViewModel() {
    val extraId = stateHandle.get<Int>(AnimeDetailActivity.EXTRA_ID)!!

    private var animeDetailJob: Job? = null

    private var translatedCache: String? = null

    private var _animeDetail3: MutableStateFlow<Resource<AnimeDetail?>> =
        MutableStateFlow(Resource.Loading())
    val animeDetail3: StateFlow<Resource<AnimeDetail?>> = _animeDetail3

    private var _animeDetail = MutableStateFlow<AnimeDetail?>(null)
    val animeDetail = _animeDetail.asStateFlow()

    private var _synopsis = MutableStateFlow<String>("")
    val synopsis = _synopsis.asStateFlow()

    private var _isTranslated = MutableStateFlow(false)
    val isTranslated = _isTranslated.asStateFlow()

    private var _isDataLoading = MutableStateFlow(false)
    val isDataLoading = _isDataLoading.asStateFlow()

    init {
        viewModelScope.launch {
            isTranslated.collectLatest { pCollect ->
                if (pCollect) {

                    val cache = translatedCache

                    if (cache != null) {
                        _synopsis.value = cache
                        return@collectLatest
                    }
                    Timber.d("Load Translate")

                    val synopsis = animeDetail.value?.synopsis.orEmpty()
                    translateRepository.getTranslateToIndo(synopsis)
                        .filter { it !is Resource.Loading }
                        .first()
                        .also {
                            if (it is Resource.Success ) {
                                val result = it.data?.result
                                if (result != null) {
                                    _synopsis.value = result
                                    translatedCache = result
                                }
                            }
                        }
                    Timber.d("Finish Translate")
                } else {
                    _synopsis.value = animeDetail.value?.synopsis.orEmpty()
                }
            }
        }

        animeDetailJob = viewModelScope.launch {
            repository.getAnimeDetail(extraId).collectLatest {
                if (it is Resource.Success) {
                    _animeDetail.value = it.data
                    _synopsis.value = it.data?.synopsis.orEmpty()
                    translatedCache = null
                    setIsTranslated(false)
                    _isDataLoading.value = false
                } else if (it is Resource.Loading) {
                    _isDataLoading.value = true
                } else if (it is Resource.Error) {
                    _isDataLoading.value = false
                }
            }
        }
    }

    fun setIsTranslated(isTranslated: Boolean) {
        _isTranslated.value = isTranslated
    }

    suspend fun getTranslateToIndo(text: String) {
        viewModelScope.launch {
            translateRepository.getTranslateToIndo(text)
                .filter { it is Resource.Error  }
        }
    }
}
