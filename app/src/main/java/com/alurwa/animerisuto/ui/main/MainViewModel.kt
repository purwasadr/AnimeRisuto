package com.alurwa.animerisuto.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alurwa.animerisuto.data.repository.auth.AuthRepository
import com.alurwa.animerisuto.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Purwa Shadr Al 'urwa on 10/05/2021
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    val user = userRepository.getUser().asLiveData()

    fun isLogged() = authRepository.isLogged()

    fun logout() {
        authRepository.logout()
    }
}
