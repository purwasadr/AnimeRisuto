package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Purwa Shadr Al 'urwa on 27/06/2021
 */

data class StudioResponse(
    @field:SerializedName("name")
    val name: String,
)
