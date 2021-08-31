package com.alurwa.animerisuto.data.repository.auth

import android.content.SharedPreferences
import androidx.core.content.edit
import com.alurwa.animerisuto.di.OAuthSharedPreferences
import com.alurwa.animerisuto.utils.SessionManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthLocalSource @Inject constructor(
    @OAuthSharedPreferences
    private val sharedPreferences: SharedPreferences
) {

    fun saveAuth(accessToken: String, refreshToken: String, isLogged: Boolean) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN, accessToken)
            putString(REFRESH_TOKEN, refreshToken)
            putBoolean(IS_USER_LOGGED, isLogged)
        }
    }

    fun refreshToken(accessToken: String, refreshToken: String) {
        sharedPreferences.edit {
            putString(SessionManager.ACCESS_TOKEN, accessToken)
            putString(SessionManager.REFRESH_TOKEN, refreshToken)
        }
    }

    fun getRefreshToken(): String? =
        sharedPreferences.getString(REFRESH_TOKEN, null)

    fun getAccessToken(): String? =
        sharedPreferences.getString(ACCESS_TOKEN, null)

    fun logOut() {
        sharedPreferences.edit(commit = true) {
            remove(ACCESS_TOKEN)
            remove(REFRESH_TOKEN)
            putBoolean(IS_USER_LOGGED, false)
        }
    }

    fun isLogged(): Boolean = sharedPreferences.getBoolean(IS_USER_LOGGED, false)

    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val IS_USER_LOGGED = "isUserLogged"
    }
}