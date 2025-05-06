package com.malabar.malabarmoviesapp.domain.interactors.tv

import arrow.core.Either
import com.malabar.core.data.params.MovieSearchParam
import com.malabar.core.failure.Failure
import com.malabar.core.interactors.UseCase
import com.malabar.malabarmoviesapp.domain.data.tv.search.TvSearchResponse
import com.malabar.malabarmoviesapp.domain.repository.tv.TvSearchRepository

class GetTvSearchResultUseCase(
    private val tvSearchRepository: TvSearchRepository
) : UseCase<TvSearchResponse, MovieSearchParam>() {
    override suspend fun run(params: MovieSearchParam): Either<Failure, TvSearchResponse> {
        return tvSearchRepository.retrieveTvSearchResult(
            query = params.query
        )
    }
}