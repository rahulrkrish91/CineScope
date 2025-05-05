package com.malabar.malabarmoviesapp.domain.repository.tv

import arrow.core.Either
import com.malabar.core.failure.Failure
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.tv.airing_today.AiringTodayResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TvAiringTodayRepository(
    private val movieApi: MovieApi
) {

    suspend fun retrievingTvAiringToday(): Flow<Either<Failure, AiringTodayResponse>> = flow {
        try {
            emit(
                Either.Right(
                    movieApi.retrievingTvAiringToday()
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
}