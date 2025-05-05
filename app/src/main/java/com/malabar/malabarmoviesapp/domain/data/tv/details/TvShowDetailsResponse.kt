package com.malabar.malabarmoviesapp.domain.data.tv.details

import com.google.gson.annotations.SerializedName
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_ORIGINAL
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_W_500
import com.malabar.core.data.genre.GenreItem
import com.malabar.core.data.production_company.ProductionCompanyItem

data class TvShowDetailsResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdrop_path: String,
    @SerializedName("created_by") val created_by: List<TvShowCreatedByItem>,
    @SerializedName("first_air_date") val first_air_date: String,
    @SerializedName("genres") val genres: List<GenreItem>,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("id") val id: Int,
    @SerializedName("in_production") val in_production: Boolean,
    @SerializedName("languages") val languages: List<String>,
    @SerializedName("last_air_date") val last_air_date: String,
    @SerializedName("last_episode_to_air") val last_episode_to_air: LastEpisodeToAirItem,
    @SerializedName("name") val name: String,
    @SerializedName("number_of_episodes") val number_of_episodes: Int,
    @SerializedName("number_of_seasons") val number_of_seasons: Int,
    @SerializedName("origin_country") val origin_country: List<String>,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("original_name") val original_name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("production_companies") val production_companies: List<ProductionCompanyItem>,
    @SerializedName("seasons") val seasons: List<TvSeasonItems>,
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("type") val type: String,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("vote_count") val vote_count: Int
){
    fun createPosterImage(): String{
        return "$MOVIE_IMAGE_W_500$poster_path"
    }

    fun createBackdropImage(): String{
        return "$MOVIE_IMAGE_ORIGINAL$backdrop_path"
    }
}
