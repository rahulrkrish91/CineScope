package com.malabar.malabarmoviesapp.domain.repository

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.MovieNowPlayingResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieUpcomingRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrieveUpcomingMovies(
        language: String,
        page: Int,
        region: String
    ): Flow<Either<Failure, MovieNowPlayingResponse>> {
        return flow {
            emit(Either.Right(movieApi.retrieveUpcomingMovies(language, page, region)))
        }.catch {
            Either.Left(Failure.ServerError(it))
        }
            .flowOn(Dispatchers.IO)
    }
}