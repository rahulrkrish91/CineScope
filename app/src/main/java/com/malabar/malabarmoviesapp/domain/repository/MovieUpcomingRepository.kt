package com.malabar.malabarmoviesapp.domain.repository

import arrow.core.Either
import arrow.core.left
import com.malabar.core.AnalyticsHelper
import com.malabar.core.base.safeApiCall
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

    fun retrieveUpcomingMovies(
        language: String,
        page: Int,
        region: String
    ): Flow<Either<Failure, MovieNowPlayingResponse>> {
        return flow {
            emit(
                Either.Right(movieApi.retrieveUpcomingMovies(language, page, region))
            )
        }
            .catch { ex ->
                AnalyticsHelper.logEvent(
                    name = "HomeScreen_UpcomingMovies",
                    params = mapOf("Exception" to ex.localizedMessage)
                )
                Either.Left(Failure.ServerError(ex.cause))
            }.flowOn(Dispatchers.IO)
    }
}