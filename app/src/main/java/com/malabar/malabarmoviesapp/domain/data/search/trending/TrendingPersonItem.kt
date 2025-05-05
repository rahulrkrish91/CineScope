package com.malabar.malabarmoviesapp.domain.data.search.trending

import com.google.gson.annotations.SerializedName
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_W_500

data class TrendingPersonItem(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val original_name: String,
    @SerializedName("media_type") val media_type: String,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("gender") val gender: Int,
    @SerializedName("known_for_department") val known_for_department: String,
    @SerializedName("profile_path") val profile_path: String
) {
    fun createProfilePath() = "$MOVIE_IMAGE_W_500$profile_path"
}
