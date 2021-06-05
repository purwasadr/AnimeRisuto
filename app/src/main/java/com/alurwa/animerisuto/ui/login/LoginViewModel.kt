package com.alurwa.animerisuto.ui.login

import androidx.lifecycle.ViewModel
import com.alurwa.animerisuto.data.IAnimeRepository
import com.alurwa.animerisuto.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Purwa Shadr Al 'urwa on 05/05/2021
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: IAnimeRepository
) : ViewModel() {

    fun getAccesToken(code: String, codeVerifier: String): Flow<Resource<Boolean>> =
        repository.getAccessToken(code, codeVerifier)
}
