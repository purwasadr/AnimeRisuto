package com.alurwa.animerisuto.model

/**
 * Created by Purwa Shadr Al 'urwa on 13/07/2021
 */

data class User(
    val id: Int,
    val name: String,
    val picture: String?,
    val gender: String?,
    val birthday: String?,
    val location: String?,
    val joinedAt: String,
    val isSupporter: Boolean?,
)
