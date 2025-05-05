package com.malabar.malabarmoviesapp.domain.data.tv.episode

import com.google.gson.annotations.SerializedName
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_W_500
import com.malabar.malabarmoviesapp.domain.data.tv.cast.TvCastItem

data class EpisodeDetails(
    @SerializedName("air_date") val air_date: String,
    @SerializedName("episode_number") val episode_number: Int,
    @SerializedName("guest_stars") val guest_stars: List<TvCastItem>,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("id") val id: Int,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("season_number") val season_number: Int,
    @SerializedName("still_path") val still_path: String,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("vote_count") val vote_count: Int
) {

    fun createStillPath() = "$MOVIE_IMAGE_W_500$still_path"
}


