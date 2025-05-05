package com.malabar.malabarmoviesapp.domain.data.tv.details

import com.google.gson.annotations.SerializedName
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_W_500

data class LastEpisodeToAirItem(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("vote_count") val vote_count: Int,
    @SerializedName("air_date") val air_date: String,
    @SerializedName("episode_number") val episode_number: Int,
    @SerializedName("episode_type") val episode_type: String,
    @SerializedName("production_code") val production_code: String,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("season_number") val season_number: Int,
    @SerializedName("show_id") val show_id: Int,
    @SerializedName("still_path") val still_path: String
){
    fun createPosterImage(): String{
        return "$MOVIE_IMAGE_W_500$still_path"
    }
}
