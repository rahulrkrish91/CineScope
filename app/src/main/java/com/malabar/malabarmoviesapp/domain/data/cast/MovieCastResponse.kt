package com.malabar.malabarmoviesapp.domain.data.cast

import com.google.gson.annotations.SerializedName

data class MovieCastResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("cast") val cast: List<MovieCastItem>
)
