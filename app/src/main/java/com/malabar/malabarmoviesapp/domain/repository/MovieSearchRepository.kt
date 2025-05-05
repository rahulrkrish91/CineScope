package com.malabar.malabarmoviesapp.domain.repository

import arrow.core.Either
import com.google.gson.Gson
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.search.MovieSearchResponse
import com.malabar.malabarmoviesapp.domain.data.search.multi.result.MovieResult
import com.malabar.malabarmoviesapp.domain.data.search.multi.result.MultiSearchResponse
import com.malabar.malabarmoviesapp.domain.data.search.multi.result.MultiSearchResult
import com.malabar.malabarmoviesapp.domain.data.search.multi.result.PersonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieSearchRepository(
    private val movieApi: MovieApi,
    private val gson: Gson
) {

    suspend fun retrieveMovieSearch(
        query: String
    ): Flow<Either<Failure, MovieSearchResponse>> = flow {
        try {
            emit(
                Either.Right(
                    movieApi.retrieveMovieSearch(query)
                )
            )
        } catch (ex: Exception) {
            emit(
                Either.Left(
                    Failure.ServerError(ex.cause)
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    suspend fun retrieveMultiSearch(
        query: String
    ): Either.Right<MutableList<MultiSearchResult>> {
        val data = movieApi.retrieveMultiSearchResponse(query)
        val processedData = mutableListOf<MultiSearchResult>()

        if (data.result.isNotEmpty()){
            for (item in data.result) {
                val mediaType = item["media_type"]?.asString ?: "movie"

                when (mediaType) {
                    "person" -> {
                        val person = gson.fromJson(item, PersonResult::class.java)
                        processedData.add(person)
                    }

                    "movie" -> {
                        val movie = gson.fromJson(item, MovieResult::class.java)
                        processedData.add(movie)
                    }
                }
            }
        }

        return Either.Right(processedData)
    }
}