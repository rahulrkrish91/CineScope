package com.malabar.core.data.genre

import com.google.gson.annotations.SerializedName

data class GenreItem(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
