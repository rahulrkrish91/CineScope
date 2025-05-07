package com.malabar.malabarmoviesapp.domain.data.tv.cast.credits

import com.google.gson.annotations.SerializedName

data class TvCastCreditsResponse(
    @SerializedName("cast") val cast: List<TvCastCreditsItem>,
    @SerializedName("id") val id: Int
)
