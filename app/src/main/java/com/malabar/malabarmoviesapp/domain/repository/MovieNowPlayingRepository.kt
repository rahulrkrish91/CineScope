package com.malabar.malabarmoviesapp.domain.repository

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.MovieNowPlayingResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieNowPlayingRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrieveNowPlayingMovies(
        language: String,
        page: Int,
        region: String
    ): Flow<Either<Failure, MovieNowPlayingResponse>> = flow {
        emit(Either.Right(movieApi.retrieveNowPlayingMovies(language, page, region)))
    }.flowOn(Dispatchers.IO)
}