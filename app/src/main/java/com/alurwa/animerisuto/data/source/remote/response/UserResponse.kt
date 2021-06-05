package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 02/06/2021
 */

data class UserResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("picture")
    val picture: String,
    @field:SerializedName("gender")
    val gender: String?,
    @field:SerializedName("birthday")
    val birthday: String?,
    @field:SerializedName("location")
    val location: String?,
    @field:SerializedName("joined_at")
    val joinedAt: String
)
