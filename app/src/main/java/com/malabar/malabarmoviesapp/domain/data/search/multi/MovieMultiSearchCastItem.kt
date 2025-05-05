package com.malabar.malabarmoviesapp.domain.data.search.multi

import com.google.gson.annotations.SerializedName

data class MovieMultiSearchCastItem(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val original_name: String,
    @SerializedName("media_type") val media_type: String,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("gender") val gender: Int,
    @SerializedName("known_for_department") val known_for_department: String,
    @SerializedName("profile_path") val profile_path: String,
    @SerializedName("known_for") val known_for: List<MovieMultiSearchCastKnowForItem>
)
