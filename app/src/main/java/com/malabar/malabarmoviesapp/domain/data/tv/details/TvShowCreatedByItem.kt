package com.malabar.malabarmoviesapp.domain.data.tv.details

import com.google.gson.annotations.SerializedName
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_W_500

data class TvShowCreatedByItem(
    @SerializedName("id") val id: Int,
    @SerializedName("credit_id") val credit_id: String,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val original_name: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("profile_path") val profile_path: String
) {
    fun createProfilePath(): String {
        return "$MOVIE_IMAGE_W_500$profile_path"
    }
}
