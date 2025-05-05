package com.malabar.core.data.belongs_to

import com.google.gson.annotations.SerializedName

data class BelongsToItem(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdrop_path: String
)
