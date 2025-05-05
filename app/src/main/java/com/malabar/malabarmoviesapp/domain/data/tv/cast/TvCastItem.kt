package com.malabar.malabarmoviesapp.domain.data.tv.cast

import com.google.gson.annotations.SerializedName
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_W_500

data class TvCastItem(
    @SerializedName("character") val character: String,
    @SerializedName("credit_id") val credit_id: String,
    @SerializedName("order") val order: Int,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("gender") val gender: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("known_for_department") val known_for_department: String,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val original_name: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("profile_path") val profile_path: String
) {
    fun createProfile() = "$MOVIE_IMAGE_W_500$profile_path"
}
