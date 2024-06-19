package com.bangkit.rechef.data.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

	@field:SerializedName("confidence")
	val confidence: Any,

	@field:SerializedName("class_name")
	val className: String
)
