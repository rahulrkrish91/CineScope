package com.malabar.malabarmoviesapp.domain.data.details

import com.google.gson.annotations.SerializedName
import com.malabar.core.data.belongs_to.BelongsToItem
import com.malabar.core.data.genre.GenreItem
import com.malabar.core.data.production_company.ProductionCompanyItem

data class MovieDetailsResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdrop_path: String,
    @SerializedName("belongs_to_collection") val belongs_to_collection: BelongsToItem,
    @SerializedName("budget") val budget: Long,
    @SerializedName("genres") val genres: List<GenreItem>,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("id") val id: Int,
    @SerializedName("imdb_id") val imdb_id: String,
    @SerializedName("origin_country") val origin_country: List<String>,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("production_companies") val production_companies: List<ProductionCompanyItem>,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("revenue") val revenue: Long,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("vote_count") val vote_count: Int
)
