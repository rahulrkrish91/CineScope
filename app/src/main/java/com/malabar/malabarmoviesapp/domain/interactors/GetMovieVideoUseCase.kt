package com.malabar.malabarmoviesapp.domain.interactors

import arrow.core.Either
import com.malabar.core.data.params.IdParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.FlowUseCase
import com.malabar.malabarmoviesapp.domain.data.video.MovieVideoResponse
import com.malabar.malabarmoviesapp.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetMovieVideoUseCase(
    private val movieDetailsRepository: MovieDetailsRepository
) : FlowUseCase<MovieVideoResponse, IdParam>() {
    override suspend fun run(params: IdParam): Flow<Either<Failure, MovieVideoResponse>> {
        return movieDetailsRepository.retrieveMovieVideo(params.id)
    }
}