package com.malabar.core.data

import com.google.gson.annotations.SerializedName

data class MovieMaxMinDateItem(
    @SerializedName("maximum") val maximum: String,
    @SerializedName("minimum") val minimum: String
)