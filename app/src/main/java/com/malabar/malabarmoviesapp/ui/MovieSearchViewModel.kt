package com.malabar.malabarmoviesapp.ui

import androidx.lifecycle.viewModelScope
import com.malabar.core.base.BaseViewModel
import com.malabar.core.data.params.MovieSearchParam
import com.malabar.malabarmoviesapp.domain.data.MoviePopularResponse
import com.malabar.malabarmoviesapp.domain.data.search.MovieSearchResponse
import com.malabar.malabarmoviesapp.domain.data.search.multi.result.MultiSearchResult
import com.malabar.malabarmoviesapp.domain.data.search.trending.TrendingPersonResult
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieSearchUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetMultiSearchUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetTrendingMoviesUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetTrendingPersonUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieSearchViewModel(
    private val getMovieSearchUseCase: GetMovieSearchUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getTrendingPersonUseCase: GetTrendingPersonUseCase,
    private val getMultiSearchUseCase: GetMultiSearchUseCase
) : BaseViewModel() {

    private val _mutableMovieSearchState =
        MutableStateFlow<MovieSearchState>(MovieSearchState.Loading)
    val movieSearchState: StateFlow<MovieSearchState> = _mutableMovieSearchState

    private val _mutableMovieTrendingState =
        MutableStateFlow<MovieTrendingState>(MovieTrendingState.Loading)
    val mutableMovieTrendingState: StateFlow<MovieTrendingState> = _mutableMovieTrendingState

    private val _mutableTrendingPersonState =
        MutableStateFlow<MovieTrendingPersonState>(MovieTrendingPersonState.Loading)
    val mutableTrendingPersonState: StateFlow<MovieTrendingPersonState> =
        _mutableTrendingPersonState

    private val _mutableMultiSearchState =
        MutableStateFlow<MovieMultiSearchState>(MovieMultiSearchState.Loading)
    val mutableMultiSearchState: StateFlow<MovieMultiSearchState> = _mutableMultiSearchState


    fun retrieveMovieSearch(query: String) {
        _mutableMovieSearchState.value = MovieSearchState.Loading
        getMovieSearchUseCase(
            MovieSearchParam(
                query = query
            )
        ) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        {},
                        { result ->
                            _mutableMovieSearchState.value = MovieSearchState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveTrendingMovies(timeWindow: String) {
        _mutableMovieTrendingState.value = MovieTrendingState.Loading
        getTrendingMoviesUseCase(
            MovieSearchParam(query = timeWindow)
        ) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        { error ->
                            handleFailure(error)
                        }, { result ->
                            _mutableMovieTrendingState.value = MovieTrendingState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveTrendingPerson(timeWindow: String) {
        _mutableTrendingPersonState.value = MovieTrendingPersonState.Loading
        getTrendingPersonUseCase(
            MovieSearchParam(query = timeWindow)
        ) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        { error -> handleFailure(error) },
                        { result ->
                            _mutableTrendingPersonState.value =
                                MovieTrendingPersonState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveMultiSearch(query: String) = viewModelScope.launch {
        getMultiSearchUseCase(MovieSearchParam(query = query)) {
            it.fold(
                { error->
                    handleFailure(error)
                },
                { result -> _mutableMultiSearchState.value = MovieMultiSearchState.Success(result) }
            )
        }
    }
}

sealed class MovieSearchState {
    object Loading : MovieSearchState()
    data class Success(val result: MovieSearchResponse) : MovieSearchState()
}

sealed class MovieTrendingState {
    object Loading : MovieTrendingState()
    data class Success(val result: MoviePopularResponse) : MovieTrendingState()
}

sealed class MovieTrendingPersonState {
    object Loading : MovieTrendingPersonState()
    data class Success(val result: TrendingPersonResult) : MovieTrendingPersonState()
}

sealed class MovieMultiSearchState {
    object Loading : MovieMultiSearchState()
    data class Success(val result: MutableList<MultiSearchResult>) : MovieMultiSearchState()
}