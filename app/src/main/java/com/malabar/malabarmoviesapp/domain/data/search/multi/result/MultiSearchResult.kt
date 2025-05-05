package com.malabar.malabarmoviesapp.domain.data.search.multi.result

import com.google.gson.JsonObject
import com.malabar.malabarmoviesapp.domain.data.search.multi.MovieMultiSearchCastKnowForItem

sealed class MultiSearchResult {
    abstract val mediaType: String
}

data class PersonResult(
    override val mediaType: String,
    val id: Int,
    val name: String,
    val original_name: String,
    val adult: Boolean,
    val popularity: Double,
    val gender: Int,
    val known_for_department: String,
    val profile_path: String,
    val known_for: List<MovieMultiSearchCastKnowForItem>,
): MultiSearchResult()

data class MovieResult(
    override val mediaType: String = "movie",
    val backdrop_path: String,
    val id: Int,
    val title: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val media_type: String,
    val adult: Boolean,
    val original_language: String,
    val genre_ids: List<Int>,
    val popularity: Double,
    val release_date: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
): MultiSearchResult()

data class MultiSearchResponse(
    val result: List<JsonObject>
)