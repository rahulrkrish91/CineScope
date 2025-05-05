package com.malabar.malabarmoviesapp.domain.data.search.multi

import com.google.gson.annotations.SerializedName

data class MovieMultiSearchResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieMultiSearchCastItem>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int
)
