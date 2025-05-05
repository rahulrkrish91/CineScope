package com.malabar.malabarmoviesapp.domain.repository

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.MoviePopularResponse
import com.malabar.malabarmoviesapp.domain.data.search.trending.TrendingPersonResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieTrendingRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrieveTrendingMovies(
        timeWindow: String
    ): Flow<Either<Failure, MoviePopularResponse>> = flow {
        try {
            emit(
                Either.Right(
                    movieApi.retrieveTrendingMovies(
                        timeWindow
                    )
                )
            )
        } catch (ex: Exception) {
            emit(Either.Left(Failure.ServerError(ex.cause)))
        }
    }

    suspend fun retrieveTrendingPersons(
        timeWindow: String
    ): Flow<Either<Failure, TrendingPersonResult>> = flow {
        try {
            emit(
                Either.Right(
                    movieApi.retrieveTrendingPerson(timeWindow)
                )
            )
        } catch (ex: Exception) {
            emit(Either.Left(Failure.ServerError(ex.cause)))
        }
    }
}