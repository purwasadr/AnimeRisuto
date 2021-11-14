package com.alurwa.animerisuto.ui.login

import androidx.lifecycle.ViewModel
import com.alurwa.animerisuto.data.Result
import com.alurwa.animerisuto.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Purwa Shadr Al 'urwa on 05/05/2021
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    fun getAccesToken(code: String, codeVerifier: String): Flow<Result<Boolean>> =
        repository.getAccessToken(code, codeVerifier)
}
