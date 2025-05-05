package com.malabar.malabarmoviesapp.domain.data.images

import com.google.gson.annotations.SerializedName

data class MovieImageResponse(
    @SerializedName("backdrops") val backdrops: List<MovieImageItem>,
    @SerializedName("id") val id: Int,
    @SerializedName("logos") val logos: List<MovieImageItem>,
    @SerializedName("posters") val posters: List<MovieImageItem>
)
