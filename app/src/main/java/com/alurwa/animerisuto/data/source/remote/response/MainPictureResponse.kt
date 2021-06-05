package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 17/05/2021
 */

data class MainPictureResponse(

    @field:SerializedName("large")
    val large: String?,

    @field:SerializedName("medium")
    val medium: String
)
