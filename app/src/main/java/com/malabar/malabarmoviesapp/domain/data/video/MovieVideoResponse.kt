package com.malabar.malabarmoviesapp.domain.data.video

import com.google.gson.annotations.SerializedName

data class MovieVideoResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: List<MovieVideoItem>
)
