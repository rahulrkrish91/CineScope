package com.malabar.malabarmoviesapp.domain.data.tv.season

import com.google.gson.annotations.SerializedName
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_W_500

data class TvSeasonDetailsResponse(
    @SerializedName("_id") val _id: String,
    @SerializedName("air_date") val air_date: String,
    @SerializedName("episodes") val episodes: List<EpisodeItem>,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("season_number") val season_number: Int,
    @SerializedName("vote_average") val vote_average: Double
) {
    fun createPoster() = "$MOVIE_IMAGE_W_500$poster_path"
}
