package com.malabar.malabarmoviesapp.domain.data

import com.google.gson.annotations.SerializedName
import com.malabar.core.data.MovieItem

data class MoviePopularResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieItem>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int
)
