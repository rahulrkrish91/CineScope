package com.malabar.malabarmoviesapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malabar.core.data.MovieMaxMinDateItem
import com.malabar.core.data.params.MovieNowPlayingParams
import com.malabar.malabarmoviesapp.domain.data.MovieNowPlayingResponse
import com.malabar.malabarmoviesapp.domain.data.MoviePopularResponse
import com.malabar.malabarmoviesapp.domain.interactors.GetNowPlayingMovieUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetPopularMovieUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetTopRatedMovieUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetUpcomingMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getNowPlayingMovieUseCase: GetNowPlayingMovieUseCase,
    private val getPopularMovieUseCase: GetPopularMovieUseCase,
    private val getTopRatedMovieUseCase: GetTopRatedMovieUseCase,
    private val getUpcomingMovieUseCase: GetUpcomingMovieUseCase
) : ViewModel() {

    private val _mutableNowPlayingMovie = MutableStateFlow(
        MovieNowPlayingState.Success(
            MovieNowPlayingResponse(
                dates = MovieMaxMinDateItem(
                    maximum = "",
                    minimum = ""
                ),
                page = 0,
                results = emptyList(),
                total_pages = 0,
                total_results = 0
            )
        )
    )
    val mutableNowPlayingMovie: StateFlow<MovieNowPlayingState>
        get() = _mutableNowPlayingMovie

    private val _mutablePopularMovie = MutableStateFlow(
        MoviePopularState.Success(
            MoviePopularResponse(
                page = 0,
                results = emptyList(),
                total_pages = 0,
                total_results = 0
            )
        )
    )
    val mutablePopularMovie: StateFlow<MoviePopularState>
        get() = _mutablePopularMovie

    private val _mutableTopRatedMovie = MutableStateFlow(
        MovieTopRatedState.Success(
            MoviePopularResponse(
                page = 0,
                results = emptyList(),
                total_pages = 0,
                total_results = 0
            )
        )
    )
    val mutableTopRatedMovie: StateFlow<MovieTopRatedState>
        get() = _mutableTopRatedMovie

    private val _mutableUpcomingMovie = MutableStateFlow(
        MovieUpcomingState.Success(
            MovieNowPlayingResponse(
                dates = MovieMaxMinDateItem(
                    maximum = "",
                    minimum = ""
                ),
                page = 0,
                results = emptyList(),
                total_pages = 0,
                total_results = 0
            )
        )
    )
    val mutableUpcomingMovie: StateFlow<MovieUpcomingState>
        get() = _mutableUpcomingMovie

    fun retrieveNowPlayingMovies(
        language: String,
        page: Int,
        region: String
    ) {
        getNowPlayingMovieUseCase(
            MovieNowPlayingParams(
                language = language,
                page = page,
                region = region
            )
        ) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        {},
                        { result ->
                            _mutableNowPlayingMovie.value = MovieNowPlayingState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrievePopularMovies(
        language: String,
        page: Int,
        region: String
    ) {
        getPopularMovieUseCase(
            MovieNowPlayingParams(
                language = language,
                page = page,
                region = region
            )
        ) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        {},
                        { result ->
                            _mutablePopularMovie.value = MoviePopularState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveTopRatedMovies(
        language: String,
        page: Int,
        region: String
    ) {
        getTopRatedMovieUseCase(
            MovieNowPlayingParams(
                language = language,
                page = page,
                region = region
            )
        ) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        {},
                        { result ->
                            _mutableTopRatedMovie.value = MovieTopRatedState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveUpcomingMovies(
        language: String,
        page: Int,
        region: String
    ) {
        getUpcomingMovieUseCase(
            MovieNowPlayingParams(
                language = language,
                page = page,
                region = region
            )
        ) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        {},
                        { result ->
                            _mutableUpcomingMovie.value = MovieUpcomingState.Success(result)
                        }
                    )
                }
            }
        }
    }

}

sealed class MovieNowPlayingState {
    data class Success(val movieNowPlayingResponse: MovieNowPlayingResponse) :
        MovieNowPlayingState()
}

sealed class MoviePopularState {
    data class Success(val moviePopularResponse: MoviePopularResponse) : MoviePopularState()
}

sealed class MovieTopRatedState {
    data class Success(val moviePopularResponse: MoviePopularResponse) : MovieTopRatedState()
}

sealed class MovieUpcomingState {
    data class Success(val movieNowPlayingResponse: MovieNowPlayingResponse) :
        MovieUpcomingState()
}