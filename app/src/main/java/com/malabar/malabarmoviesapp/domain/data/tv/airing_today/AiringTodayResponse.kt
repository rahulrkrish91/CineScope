package com.malabar.malabarmoviesapp.domain.data.tv.airing_today

import com.google.gson.annotations.SerializedName
import com.malabar.malabarmoviesapp.domain.data.tv.TvItem

data class AiringTodayResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<TvItem>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int
)
