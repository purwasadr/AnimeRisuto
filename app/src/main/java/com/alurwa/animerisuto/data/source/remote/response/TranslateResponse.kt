package com.alurwa.animerisuto.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TranslateResponse(

	@field:SerializedName("data")
	val data: TranslateDataResponse? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
