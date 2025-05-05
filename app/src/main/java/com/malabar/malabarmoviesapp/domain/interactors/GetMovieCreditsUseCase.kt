package com.malabar.malabarmoviesapp.domain.interactors

import arrow.core.Either
import com.malabar.core.data.params.MovieCastParams
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.UseCase
import com.malabar.malabarmoviesapp.domain.data.cast.MovieCastResponse
import com.malabar.malabarmoviesapp.domain.repository.MovieCastRepository

class GetMovieCreditsUseCase(
    private val movieCastRepository: MovieCastRepository
) : UseCase<MovieCastResponse, MovieCastParams>() {
    override suspend fun run(params: MovieCastParams): Either<Failure, MovieCastResponse> {
        return movieCastRepository.retrieveMovieCredits(
            movieId = params.movieId,
            language = params.language
        )
    }
}