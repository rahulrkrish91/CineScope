package com.malabar.malabarmoviesapp.domain.interactors

import arrow.core.Either
import com.malabar.core.data.params.IdParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.cast.credits.MovieCastCreditsResponse
import com.malabar.malabarmoviesapp.domain.repository.MovieCastCreditsRepository
import kotlinx.coroutines.flow.Flow

class GetMovieCastCreditsUseCase(
    private val movieCastCreditsRepository: MovieCastCreditsRepository
) : FlowUseCase<MovieCastCreditsResponse, IdParam>() {
    override suspend fun run(params: IdParam): Flow<Either<Failure, MovieCastCreditsResponse>> {
        return movieCastCreditsRepository.retrievePersonMovieCredits(
            personId = params.id
        )
    }
}