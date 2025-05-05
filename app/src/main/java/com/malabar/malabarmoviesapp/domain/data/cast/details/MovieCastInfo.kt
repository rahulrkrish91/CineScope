package com.malabar.malabarmoviesapp.domain.data.cast.details

import com.google.gson.annotations.SerializedName
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_W_500

data class MovieCastInfo(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("also_known_as") val also_known_as: List<String>,
    @SerializedName("biography") val biography: String,
    @SerializedName("birthday") var birthday: String?="",
    @SerializedName("gender") val gender: Int,
    @SerializedName("homepage") var homepage: String? = "",
    @SerializedName("id") val id: Int,
    @SerializedName("imdb_id") val imdb_id: String,
    @SerializedName("known_for_department") val known_for_department: String,
    @SerializedName("name") val name: String,
    @SerializedName("place_of_birth") val place_of_birth: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("profile_path") var profile_path: String? = ""
) {
    fun getProfilePath(): String {
        return "$MOVIE_IMAGE_W_500$profile_path"
    }
}
