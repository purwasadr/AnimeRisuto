package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 04/05/2021
 */

data class AccessTokenResponse(
    @field:SerializedName("token_type")
    val tokenType: String,

    @field:SerializedName("expires_in")
    val expiresIn: Int,

    @field:SerializedName("access_token")
    val accessToken: String,

    @field:SerializedName("refresh_token")
    val refreshToken: String
)
