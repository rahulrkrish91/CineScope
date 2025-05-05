package com.malabar.malabarmoviesapp.domain.repository.tv

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.tv.airing_today.AiringTodayResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvTrendingRepository(
    private val movieApi: MovieApi
) {
    suspend fun retrieveTrendingTv(
        time_window: String
    ): Flow<Either<Failure, AiringTodayResponse>> = flow {
        try {
            emit(
                Either.Right(
                    movieApi.retrieveTrendingTv(time_window)
                )
            )
        } catch (ex: Exception) {
            emit(
                Either.Left(Failure.ServerError(ex.cause))
            )
        }
    }
}