package com.malabar.malabarmoviesapp.domain.data.tv

import com.google.gson.annotations.SerializedName
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_ORIGINAL
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_W_500

data class TvItem(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdrop_path: String,
    @SerializedName("genre_ids") val genre_ids: List<Int>,
    @SerializedName("id") val id: Int,
    @SerializedName("origin_country") val origin_country: List<String>,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("original_name") val original_name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("first_air_date") val first_air_date: String,
    @SerializedName("name") val name: String,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("vote_count") val vote_count: Int
){
    fun createBackdropImage(): String{
        return "$MOVIE_IMAGE_ORIGINAL$backdrop_path"
    }

    fun createPosterImage(): String{
        return "$MOVIE_IMAGE_W_500$poster_path"
    }
}
