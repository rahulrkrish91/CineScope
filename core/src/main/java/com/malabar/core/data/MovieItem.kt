package com.malabar.core.data

import com.google.gson.annotations.SerializedName
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_ORIGINAL
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_W_500

data class MovieItem(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdrop_path: String,
    @SerializedName("genre_ids") val genre_ids: List<Int>,
    @SerializedName("id") val id: Int,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
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
