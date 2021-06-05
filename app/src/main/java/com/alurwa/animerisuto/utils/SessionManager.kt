package com.alurwa.animerisuto.utils

import android.content.Context
import androidx.core.content.edit
import com.alurwa.animerisuto.constant.OAUTH_PREFERENCES

/**
 * Created by Purwa Shadr Al 'urwa on 10/05/2021
 */

class SessionManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(
        OAUTH_PREFERENCES,
        Context.MODE_PRIVATE
    )

    fun saveAuth(accessToken: String, refreshToken: String, isLogged: Boolean) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN, accessToken)
            putString(REFRESH_TOKEN, refreshToken)
            putBoolean(IS_USER_LOGGED, isLogged)
        }
    }

    fun refreshToken(accessToken: String, refreshToken: String) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN, accessToken)
            putString(REFRESH_TOKEN, refreshToken)
        }
    }

    fun getRefreshToken(): String? = sharedPreferences.getString(REFRESH_TOKEN, null)

    fun getAccessToken(): String? = sharedPreferences.getString(ACCESS_TOKEN, null)

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
