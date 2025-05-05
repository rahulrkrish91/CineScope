package com.malabar.malabarmoviesapp.domain.repository

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.MoviePopularResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieTopRatedRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrieveTopRatedMovies(
        language: String,
        page: Int,
        region: String
    ): Flow<Either<Failure, MoviePopularResponse>> = flow {
        emit(Either.Right(movieApi.retrieveTopRatedMovies(language, page, region)))
    }.flowOn(Dispatchers.IO)
}