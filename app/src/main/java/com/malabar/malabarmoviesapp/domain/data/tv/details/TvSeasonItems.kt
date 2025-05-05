package com.malabar.malabarmoviesapp.domain.data.tv.details

import com.google.gson.annotations.SerializedName
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_W_500

data class TvSeasonItems(
    @SerializedName("air_date") val air_date: String,
    @SerializedName("episode_count") val episode_count: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") var overview: String? = "",
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("season_number") val season_number: Int,
    @SerializedName("vote_average") val vote_average: Double
){
    fun createPosterImage(): String{
        return "$MOVIE_IMAGE_W_500$poster_path"
    }
}
