package com.malabar.malabarmoviesapp.domain.data.cast.credits

import com.google.gson.annotations.SerializedName

data class MovieCastCreditsResponse(
    @SerializedName("cast") val cast: List<MovieCastCreditsItem>,
    @SerializedName("id") val id: Int
)
