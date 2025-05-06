package com.malabar.malabarmoviesapp.domain.data.tv.search

import com.google.gson.annotations.SerializedName

data class TvSearchResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<TvSearchItem>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int
)
