package com.malabar.malabarmoviesapp.ui

import androidx.lifecycle.viewModelScope
import com.malabar.core.AppConstants.Companion.MOVIE_DEFAULT_LANG
import com.malabar.core.base.BaseViewModel
import com.malabar.core.data.params.IdParam
import com.malabar.core.data.params.MovieCastParams
import com.malabar.core.data.params.RecommendedMovieParams
import com.malabar.malabarmoviesapp.domain.data.cast.MovieCastResponse
import com.malabar.malabarmoviesapp.domain.data.cast.credits.MovieCastCreditsResponse
import com.malabar.malabarmoviesapp.domain.data.cast.details.MovieCastInfo
import com.malabar.malabarmoviesapp.domain.data.details.MovieDetailsResponse
import com.malabar.malabarmoviesapp.domain.data.images.MovieImageResponse
import com.malabar.malabarmoviesapp.domain.data.recommended.MovieRecommendedResponse
import com.malabar.malabarmoviesapp.domain.data.reviews.MovieReviewResponse
import com.malabar.malabarmoviesapp.domain.data.video.MovieVideoResponse
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieCastCreditsUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieCastInfoUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieCreditsUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieDetailsUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieImagesUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieReviewsUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieVideoUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetRecommendedMovieUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetSimilarMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val getMovieCastInfoUseCase: GetMovieCastInfoUseCase,
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getMovieImagesUseCase: GetMovieImagesUseCase,
    private val getMovieVideoUseCase: GetMovieVideoUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getMovieCastCreditsUseCase: GetMovieCastCreditsUseCase
) : BaseViewModel() {

    private val _mutableDetailsState =
        MutableStateFlow<MovieDetailsState>(MovieDetailsState.Loading)
    val mutableDetailsState: StateFlow<MovieDetailsState> = _mutableDetailsState

    private val _mutableCreditsState =
        MutableStateFlow<MovieCreditsState>(MovieCreditsState.Loading)
    val mutableCreditsState: StateFlow<MovieCreditsState> = _mutableCreditsState

    private val _mutableCastDetailsState =
        MutableStateFlow<MovieCaseDetailsState>(MovieCaseDetailsState.Loading)
    val mutableCastDetailsState: StateFlow<MovieCaseDetailsState> = _mutableCastDetailsState

    private val _mutableRecommendedState =
        MutableStateFlow<MovieRecommendedState>(MovieRecommendedState.Loading)
    val mutableRecommendedState: StateFlow<MovieRecommendedState> = _mutableRecommendedState

    private val _mutableSimilarState =
        MutableStateFlow<MovieSimilarState>(MovieSimilarState.Loading)
    val mutableSimilarState: StateFlow<MovieSimilarState> = _mutableSimilarState

    private val _mutableMovieImageState =
        MutableStateFlow<MovieImagesState>(MovieImagesState.Loading)
    val mutableMovieImageState: StateFlow<MovieImagesState> = _mutableMovieImageState

    private val _mutableMovieVideoState =
        MutableStateFlow<MovieVideoState>(MovieVideoState.Loading)
    val mutableMovieVideoState: StateFlow<MovieVideoState> = _mutableMovieVideoState

    private val _mutableMovieReviewState =
        MutableStateFlow<MovieReviewsState>(MovieReviewsState.Loading)
    val mutableMovieReviewState: StateFlow<MovieReviewsState> = _mutableMovieReviewState

    private val _mutableMovieCastCreditState =
        MutableStateFlow<MovieCastCreditState>(MovieCastCreditState.Loading)
    val mutableMovieCastCreditState: StateFlow<MovieCastCreditState> = _mutableMovieCastCreditState


    fun retrieveMovieDetails(id: Int) = viewModelScope.launch {
        _mutableDetailsState.value = MovieDetailsState.Loading
        getMovieDetailsUseCase(IdParam(id)) {
            it.fold(
                {}, { result -> _mutableDetailsState.value = MovieDetailsState.Success(result) }
            )
        }
    }

    fun retrieveMovieCredits(id: Int) = viewModelScope.launch {
        _mutableCreditsState.value = MovieCreditsState.Loading
        getMovieCreditsUseCase(MovieCastParams(id, MOVIE_DEFAULT_LANG)) {
            it.fold(
                {}, { result -> _mutableCreditsState.value = MovieCreditsState.Success(result) }
            )
        }
    }

    fun retrieveMovieCastDetails(personId: Int) = viewModelScope.launch {
        _mutableCastDetailsState.value = MovieCaseDetailsState.Loading
        getMovieCastInfoUseCase(IdParam(personId)) {
            it.fold(
                {},
                { result -> _mutableCastDetailsState.value = MovieCaseDetailsState.Success(result) }
            )
        }
    }

    fun retrieveRecommendedMovies(movieId: Int) {
        _mutableRecommendedState.value = MovieRecommendedState.Loading
        getRecommendedMovieUseCase(RecommendedMovieParams(movieId, MOVIE_DEFAULT_LANG, 1)) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        {},
                        { result ->
                            _mutableRecommendedState.value = MovieRecommendedState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveSimilarMovies(movieId: Int) {
        _mutableSimilarState.value = MovieSimilarState.Loading
        getSimilarMoviesUseCase(RecommendedMovieParams(movieId, MOVIE_DEFAULT_LANG, 1)) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        {},
                        { result ->
                            _mutableSimilarState.value = MovieSimilarState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveMovieImages(movieId: Int) {
        _mutableMovieImageState.value = MovieImagesState.Loading
        getMovieImagesUseCase(IdParam(movieId)) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        {},
                        { result ->
                            _mutableMovieImageState.value = MovieImagesState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveMovieVideo(movieId: Int) {
        _mutableMovieVideoState.value = MovieVideoState.Loading
        getMovieVideoUseCase(IdParam(movieId)) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        {},
                        { result ->
                            _mutableMovieVideoState.value = MovieVideoState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveMovieReviews(movieId: Int) = viewModelScope.launch {
        _mutableMovieReviewState.value = MovieReviewsState.Loading
        getMovieReviewsUseCase(IdParam(movieId)) {
            it.fold(
                { error -> handleFailure(error) },
                { result -> _mutableMovieReviewState.value = MovieReviewsState.Success(result) }
            )
        }
    }

    fun retrieveMovieCastCredits(personId: Int) {
        _mutableMovieCastCreditState.value = MovieCastCreditState.Loading
        getMovieCastCreditsUseCase(IdParam(personId)) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        { error ->
                            handleFailure(error)
                        },
                        { result ->
                            _mutableMovieCastCreditState.value =
                                MovieCastCreditState.Success(result)
                        }
                    )
                }
            }
        }
    }
}

sealed class MovieDetailsState {
    object Loading : MovieDetailsState()
    data class Success(val result: MovieDetailsResponse) : MovieDetailsState()
}

sealed class MovieCreditsState {
    object Loading : MovieCreditsState()
    data class Success(val result: MovieCastResponse) : MovieCreditsState()
}

sealed class MovieCaseDetailsState {
    object Loading : MovieCaseDetailsState()
    data class Success(val result: MovieCastInfo) : MovieCaseDetailsState()
}

sealed class MovieRecommendedState {
    object Loading : MovieRecommendedState()
    data class Success(val result: MovieRecommendedResponse) : MovieRecommendedState()
}

sealed class MovieSimilarState {
    object Loading : MovieSimilarState()
    data class Success(val result: MovieRecommendedResponse) : MovieSimilarState()
}

sealed class MovieImagesState {
    object Loading : MovieImagesState()
    data class Success(val result: MovieImageResponse) : MovieImagesState()
}

sealed class MovieVideoState {
    object Loading : MovieVideoState()
    data class Success(val result: MovieVideoResponse) : MovieVideoState()
}

sealed class MovieReviewsState {
    object Loading : MovieReviewsState()
    data class Success(val result: MovieReviewResponse) : MovieReviewsState()
}

sealed class MovieCastCreditState {
    object Loading : MovieCastCreditState()
    data class Success(val result: MovieCastCreditsResponse) : MovieCastCreditState()
}