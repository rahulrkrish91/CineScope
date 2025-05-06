package com.malabar.malabarmoviesapp.ui.tv

import androidx.lifecycle.viewModelScope
import com.malabar.core.base.BaseViewModel
import com.malabar.core.data.params.IdParam
import com.malabar.core.data.params.MovieSearchParam
import com.malabar.core.data.params.TvEpisodeParams
import com.malabar.core.data.params.TvSeasonParams
import com.malabar.malabarmoviesapp.domain.data.cast.MovieCastResponse
import com.malabar.malabarmoviesapp.domain.data.search.MovieSearchResponse
import com.malabar.malabarmoviesapp.domain.data.tv.airing_today.AiringTodayResponse
import com.malabar.malabarmoviesapp.domain.data.tv.details.TvShowDetailsResponse
import com.malabar.malabarmoviesapp.domain.data.tv.episode.EpisodeDetails
import com.malabar.malabarmoviesapp.domain.data.tv.search.TvSearchResponse
import com.malabar.malabarmoviesapp.domain.data.tv.season.TvSeasonDetailsResponse
import com.malabar.malabarmoviesapp.domain.data.video.MovieVideoResponse
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetPopularTvShowUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTopRatedTvShowUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTrendingTvShowsUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTvAiringTodayUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTvCreditsUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTvEpisodeDetailsUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTvEpisodeVideoUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTvOnTheAirUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTvRecommendationsUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTvSearchResultUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTvSeasonDetailsUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTvSeasonVideoUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTvSeriesDetailsUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTvSeriesVideoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TvViewModel(
    private val getTvAiringTodayUseCase: GetTvAiringTodayUseCase,
    private val getTvOnTheAirUseCase: GetTvOnTheAirUseCase,
    private val getPopularTvShowUseCase: GetPopularTvShowUseCase,
    private val getTopRatedTvShowUseCase: GetTopRatedTvShowUseCase,
    private val getTvSeriesDetailsUseCase: GetTvSeriesDetailsUseCase,
    private val getTvSeasonDetailsUseCase: GetTvSeasonDetailsUseCase,
    private val getTvCreditsUseCase: GetTvCreditsUseCase,
    private val getTvEpisodeDetailsUseCase: GetTvEpisodeDetailsUseCase,
    private val getTrendingTvShowUseCase: GetTrendingTvShowsUseCase,
    private val getTvRecommendationsUseCase: GetTvRecommendationsUseCase,
    private val getTvSeriesVideoUseCase: GetTvSeriesVideoUseCase,
    private val getTvSeasonVideoUseCase: GetTvSeasonVideoUseCase,
    private val getTvEpisodeVideoUseCase: GetTvEpisodeVideoUseCase,
    private val getTvSearchResultUseCase: GetTvSearchResultUseCase
) : BaseViewModel() {

    private val _mutableAiringTodayState =
        MutableStateFlow<TvAiringTodayState>(TvAiringTodayState.Loading)
    val airingTodayState: StateFlow<TvAiringTodayState> = _mutableAiringTodayState

    private val _mutableOnTheAirState =
        MutableStateFlow<TvOnTheAirState>(TvOnTheAirState.Loading)
    val mutableOnTheAirState: StateFlow<TvOnTheAirState> = _mutableOnTheAirState

    private val _mutablePopularTvState =
        MutableStateFlow<PopularTvShowState>(PopularTvShowState.Loading)
    val mutablePopularTvState: StateFlow<PopularTvShowState> = _mutablePopularTvState

    private val _mutableTopRatedTvState =
        MutableStateFlow<TopRatedTvShowState>(TopRatedTvShowState.Loading)
    val mutableTopRatedTvState: StateFlow<TopRatedTvShowState> = _mutableTopRatedTvState

    private val _mutableTvSeriesDetailsState =
        MutableStateFlow<TvSeriesDetailsState>(TvSeriesDetailsState.Loading)
    val mutableTvSeriesDetailsState: StateFlow<TvSeriesDetailsState> = _mutableTvSeriesDetailsState

    private val _mutableTvSeasonDetailsState =
        MutableStateFlow<TvSeasonDetailsState>(TvSeasonDetailsState.Loading)
    val mutableTvSeasonDetailsState: StateFlow<TvSeasonDetailsState> = _mutableTvSeasonDetailsState

    private val _mutableTvCreditsState = MutableStateFlow<TvCreditsState>(TvCreditsState.Loading)
    val mutableTvCreditsState: StateFlow<TvCreditsState> = _mutableTvCreditsState

    private val _mutableTvEpisodeDetailsState =
        MutableStateFlow<TvEpisodeDetailsState>(TvEpisodeDetailsState.Loading)
    val mutableTvEpisodeDetailsState: StateFlow<TvEpisodeDetailsState> =
        _mutableTvEpisodeDetailsState

    private val _mutableTrendingTvState = MutableStateFlow<TvTrendingState>(TvTrendingState.Loading)
    val mutableTrendingTvState: StateFlow<TvTrendingState> = _mutableTrendingTvState

    private val _mutableTvRecommendationState = MutableStateFlow<TvRecommendationState>(
        TvRecommendationState.Loading
    )
    val mutableTvRecommendationState: StateFlow<TvRecommendationState> =
        _mutableTvRecommendationState

    private val _mutableTvVideoState =
        MutableStateFlow<TvSeriesVideoState>(TvSeriesVideoState.Loading)
    val mutableTvVideoState: StateFlow<TvSeriesVideoState> = _mutableTvVideoState

    private val _mutableTvSeasonVideoState =
        MutableStateFlow<TvSeasonVideoState>(TvSeasonVideoState.Loading)
    val mutableTvSeasonVideoState: StateFlow<TvSeasonVideoState> = _mutableTvSeasonVideoState

    private val _mutableTvEpisodeVideoState =
        MutableStateFlow<TvEpisodeVideoState>(TvEpisodeVideoState.Loading)
    val mutableTvEpisodeVideoState: StateFlow<TvEpisodeVideoState> = _mutableTvEpisodeVideoState

    private val _mutableTvSearchState = MutableStateFlow<TvSearchState>(TvSearchState.Loading)
    val mutableTvSearchState: StateFlow<TvSearchState> = _mutableTvSearchState

    fun retrieveAiringToday() {
        _mutableAiringTodayState.value = TvAiringTodayState.Loading
        getTvAiringTodayUseCase(Unit) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        { error ->
                            handleFailure(error)
                        },
                        { result ->
                            _mutableAiringTodayState.value = TvAiringTodayState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveOnAir() {
        _mutableOnTheAirState.value = TvOnTheAirState.Loading
        getTvOnTheAirUseCase(Unit) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        { error ->
                            handleFailure(error)
                        },
                        { result ->
                            _mutableOnTheAirState.value = TvOnTheAirState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrievePopularTv() {
        _mutablePopularTvState.value = PopularTvShowState.Loading
        getPopularTvShowUseCase(Unit) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        { error ->
                            handleFailure(error)
                        },
                        { result ->
                            _mutablePopularTvState.value = PopularTvShowState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveTopRatedTv() {
        _mutableTopRatedTvState.value = TopRatedTvShowState.Loading
        getTopRatedTvShowUseCase(Unit) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        { error ->
                            handleFailure(error)
                        },
                        { result ->
                            _mutableTopRatedTvState.value = TopRatedTvShowState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveTvSeriesDetails(id: Int) = viewModelScope.launch {
        _mutableTvSeriesDetailsState.value = TvSeriesDetailsState.Loading
        getTvSeriesDetailsUseCase(IdParam(id)) {
            it.fold(
                { error ->
                    handleFailure(error)
                },
                { result ->
                    _mutableTvSeriesDetailsState.value = TvSeriesDetailsState.Success(result)
                }
            )
        }
    }

    fun retrieveTvSeasonDetails(seriesId: Int, seasonNumber: Int) = viewModelScope.launch {
        _mutableTvSeasonDetailsState.value = TvSeasonDetailsState.Loading
        getTvSeasonDetailsUseCase(TvSeasonParams(seriesId, seasonNumber)) {
            it.fold(
                { error ->
                    handleFailure(error)
                },
                { result ->
                    _mutableTvSeasonDetailsState.value = TvSeasonDetailsState.Success(result)
                }
            )
        }
    }

    fun retrieveTvCredits(seriesId: Int) {
        _mutableTvCreditsState.value = TvCreditsState.Loading
        getTvCreditsUseCase(IdParam(seriesId)) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        { error ->
                            handleFailure(error)
                        },
                        { result ->
                            _mutableTvCreditsState.value = TvCreditsState.Success(result)
                        }
                    )
                }
            }

        }
    }

    fun retrieveTvEpisodeDetails(seriesId: Int, seasonNumber: Int, episodeNumber: Int) =
        viewModelScope.launch {
            _mutableTvEpisodeDetailsState.value = TvEpisodeDetailsState.Loading
            getTvEpisodeDetailsUseCase(TvEpisodeParams(seriesId, seasonNumber, episodeNumber)) {
                it.fold(
                    { error ->
                        handleFailure(error)
                    },
                    { result ->
                        _mutableTvEpisodeDetailsState.value = TvEpisodeDetailsState.Success(result)
                    }
                )
            }

        }

    fun retrieveTrendingTvShows(time_window: String) {
        _mutableTrendingTvState.value = TvTrendingState.Loading
        getTrendingTvShowUseCase(MovieSearchParam(query = time_window)) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        { failure -> handleFailure(failure) },
                        { result ->
                            _mutableTrendingTvState.value = TvTrendingState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveTvRecommendations(seriesId: Int) {
        _mutableTvRecommendationState.value = TvRecommendationState.Loading
        getTvRecommendationsUseCase(IdParam(seriesId)) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        { error -> handleFailure(error) },
                        { result ->
                            _mutableTvRecommendationState.value =
                                TvRecommendationState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveTvVideo(seriesId: Int) {
        _mutableTvVideoState.value = TvSeriesVideoState.Loading
        getTvSeriesVideoUseCase(IdParam(seriesId)) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        { error -> handleFailure(error) },
                        { result ->
                            _mutableTvVideoState.value = TvSeriesVideoState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveTvSeasonVideo(seriesId: Int, seasonNumber: Int) {
        _mutableTvSeasonVideoState.value = TvSeasonVideoState.Loading
        getTvSeasonVideoUseCase(TvSeasonParams(seriesId, seasonNumber)) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        { error -> handleFailure(error) },
                        { result ->
                            _mutableTvSeasonVideoState.value = TvSeasonVideoState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveTvEpisodeVideo(seriesId: Int, seasonNumber: Int, episodeNumber: Int) {
        _mutableTvEpisodeVideoState.value = TvEpisodeVideoState.Loading
        getTvEpisodeVideoUseCase(TvEpisodeParams(seriesId, seasonNumber, episodeNumber)) {
            viewModelScope.launch {
                it.collect {
                    it.fold(
                        { error -> handleFailure(error) },
                        { result ->
                            _mutableTvEpisodeVideoState.value = TvEpisodeVideoState.Success(result)
                        }
                    )
                }
            }
        }
    }

    fun retrieveTvSearch(query: String) = viewModelScope.launch {
        _mutableTvSearchState.value = TvSearchState.Loading
        getTvSearchResultUseCase(MovieSearchParam(query = query)) {
            it.fold(
                { error -> handleFailure(error) },
                { result -> _mutableTvSearchState.value = TvSearchState.Success(result) }
            )
        }
    }
}

sealed class TvAiringTodayState {
    object Loading : TvAiringTodayState()
    data class Success(val data: AiringTodayResponse) : TvAiringTodayState()
}

sealed class TvOnTheAirState {
    object Loading : TvOnTheAirState()
    data class Success(val data: AiringTodayResponse) : TvOnTheAirState()
}

sealed class PopularTvShowState {
    object Loading : PopularTvShowState()
    data class Success(val data: AiringTodayResponse) : PopularTvShowState()
}

sealed class TopRatedTvShowState {
    object Loading : TopRatedTvShowState()
    data class Success(val data: AiringTodayResponse) : TopRatedTvShowState()
}

sealed class TvSeriesDetailsState {
    object Loading : TvSeriesDetailsState()
    data class Success(val data: TvShowDetailsResponse) : TvSeriesDetailsState()
}

sealed class TvSeasonDetailsState {
    object Loading : TvSeasonDetailsState()
    data class Success(val data: TvSeasonDetailsResponse) : TvSeasonDetailsState()
}

sealed class TvCreditsState {
    object Loading : TvCreditsState()
    data class Success(val data: MovieCastResponse) : TvCreditsState()
}

sealed class TvEpisodeDetailsState {
    object Loading : TvEpisodeDetailsState()
    data class Success(val data: EpisodeDetails) : TvEpisodeDetailsState()
}

sealed class TvTrendingState {
    object Loading : TvTrendingState()
    data class Success(val data: AiringTodayResponse) : TvTrendingState()
}

sealed class TvRecommendationState {
    object Loading : TvRecommendationState()
    data class Success(val data: AiringTodayResponse) : TvRecommendationState()
}

sealed class TvSeriesVideoState {
    object Loading : TvSeriesVideoState()
    data class Success(val data: MovieVideoResponse) : TvSeriesVideoState()
}

sealed class TvSeasonVideoState {
    object Loading : TvSeasonVideoState()
    data class Success(val data: MovieVideoResponse) : TvSeasonVideoState()
}

sealed class TvEpisodeVideoState {
    object Loading : TvEpisodeVideoState()
    data class Success(val data: MovieVideoResponse) : TvEpisodeVideoState()
}

sealed class TvSearchState {
    object Loading : TvSearchState()
    data class Success(val data: TvSearchResponse) : TvSearchState()
}