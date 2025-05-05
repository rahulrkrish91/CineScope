package com.malabar.malabarmoviesapp.domain.data.reviews

import com.google.gson.annotations.SerializedName

data class MovieReviewResponse(
    @SerializedName("id")  val id: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieReviewItem>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int
)
