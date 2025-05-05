package com.malabar.malabarmoviesapp.domain.data.search.multi

import com.google.gson.annotations.SerializedName

data class MovieMultiSearchMovieItem(
    @SerializedName("backdrop_path") val backdrop_path: String,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("media_type") val media_type: String,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("genre_ids") val genre_ids: List<Int>,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("vote_count") val vote_count: Int
)