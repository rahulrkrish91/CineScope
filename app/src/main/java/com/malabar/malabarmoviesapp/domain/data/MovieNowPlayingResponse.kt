package com.malabar.malabarmoviesapp.domain.data

import com.google.gson.annotations.SerializedName
import com.malabar.core.data.MovieItem
import com.malabar.core.data.MovieMaxMinDateItem

data class MovieNowPlayingResponse(
    @SerializedName("dates") val dates: MovieMaxMinDateItem,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieItem>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int
)
