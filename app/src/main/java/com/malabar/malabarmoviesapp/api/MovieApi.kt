package com.malabar.malabarmoviesapp.api

import com.malabar.malabarmoviesapp.domain.data.MovieNowPlayingResponse
import com.malabar.malabarmoviesapp.domain.data.MoviePopularResponse
import com.malabar.malabarmoviesapp.domain.data.cast.MovieCastResponse
import com.malabar.malabarmoviesapp.domain.data.cast.details.MovieCastInfo
import com.malabar.malabarmoviesapp.domain.data.details.MovieDetailsResponse
import com.malabar.malabarmoviesapp.domain.data.images.MovieImageResponse
import com.malabar.malabarmoviesapp.domain.data.recommended.MovieRecommendedResponse
import com.malabar.malabarmoviesapp.domain.data.reviews.MovieReviewResponse
import com.malabar.malabarmoviesapp.domain.data.search.MovieSearchResponse
import com.malabar.malabarmoviesapp.domain.data.search.multi.result.MultiSearchResponse
import com.malabar.malabarmoviesapp.domain.data.search.trending.TrendingPersonResult
import com.malabar.malabarmoviesapp.domain.data.tv.airing_today.AiringTodayResponse
import com.malabar.malabarmoviesapp.domain.data.tv.details.TvShowDetailsResponse
import com.malabar.malabarmoviesapp.domain.data.tv.episode.EpisodeDetails
import com.malabar.malabarmoviesapp.domain.data.tv.search.TvSearchResponse
import com.malabar.malabarmoviesapp.domain.data.tv.season.TvSeasonDetailsResponse
import com.malabar.malabarmoviesapp.domain.data.video.MovieVideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/now_playing")
    suspend fun retrieveNowPlayingMovies(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): MovieNowPlayingResponse

    @GET("movie/popular")
    suspend fun retrievePopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): MoviePopularResponse

    @GET("movie/top_rated")
    suspend fun retrieveTopRatedMovies(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): MoviePopularResponse

    @GET("movie/upcoming")
    suspend fun retrieveUpcomingMovies(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): MovieNowPlayingResponse

    @GET("movie/{movie_id}")
    suspend fun retrieveMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDetailsResponse

    @GET("movie/{movie_id}/credits")
    suspend fun retrieveMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): MovieCastResponse

    @GET("person/{person_id}")
    suspend fun retrieveMovieCastDetails(
        @Path("person_id") personId: Int
    ): MovieCastInfo

    @GET("movie/{movie_id}/recommendations")
    suspend fun retrieveMovieRecommendations(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MovieRecommendedResponse

    @GET("movie/{movie_id}/similar")
    suspend fun retrieveMovieSimilar(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MovieRecommendedResponse

    @GET("movie/{movie_id}/images")
    suspend fun retrieveMovieImages(
        @Path("movie_id") movieId: Int
    ): MovieImageResponse

    @GET("movie/{movie_id}/videos")
    suspend fun retrieveMovieVideo(
        @Path("movie_id") movieId: Int
    ): MovieVideoResponse

    @GET("search/movie")
    suspend fun retrieveMovieSearch(
        @Query("query") query: String
    ): MovieSearchResponse

    @GET("search/multi")
    suspend fun retrieveMultiSearchResponse(
        @Query("query") query: String
    ): MultiSearchResponse

    @GET("trending/movie/{time_window}")
    suspend fun retrieveTrendingMovies(
        @Path("time_window") timeWindow: String
    ): MoviePopularResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun retrieveMovieReview(
        @Path("movie_id") movieId: Int
    ): MovieReviewResponse

    @GET("trending/person/{time_window}")
    suspend fun retrieveTrendingPerson(
        @Path("time_window") timeWindow: String
    ): TrendingPersonResult

    // Tv
    @GET("tv/airing_today")
    suspend fun retrievingTvAiringToday(): AiringTodayResponse

    @GET("tv/on_the_air")
    suspend fun retrieveOnTheAir(): AiringTodayResponse

    @GET("tv/popular")
    suspend fun retrievePopularTv(): AiringTodayResponse

    @GET("tv/top_rated")
    suspend fun retrieveTopRatedTv(): AiringTodayResponse

    @GET("tv/{series_id}/credits")
    suspend fun retrieveTvCredits(
        @Path("series_id") seriesId: Int
    ): MovieCastResponse

    @GET("tv/{series_id}")
    suspend fun retrieveTvSeriesDetails(
        @Path("series_id") seriesId: Int
    ): TvShowDetailsResponse

    @GET("tv/{series_id}/season/{season_number}")
    suspend fun retrieveSeasonDetails(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int
    ): TvSeasonDetailsResponse

    @GET("tv/{series_id}/season/{season_number}/episode/{episode_number}")
    suspend fun retrieveEpisodeDetails(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int
    ): EpisodeDetails

    @GET("trending/tv/{time_window}")
    suspend fun retrieveTrendingTv(
        @Path("time_window") time_window: String
    ): AiringTodayResponse

    @GET("tv/{series_id}/recommendations")
    suspend fun retrieveRecommendedTvShows(
        @Path("series_id") seriesId: Int
    ): AiringTodayResponse

    @GET("tv/{series_id}/videos")
    suspend fun retrieveTvVideo(
        @Path("series_id") seriesId: Int
    ): MovieVideoResponse

    @GET("tv/{series_id}/season/{season_number}/videos")
    suspend fun retrieveTvSeasonVideo(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int
    ): MovieVideoResponse

    @GET("tv/{series_id}/season/{season_number}/episode/{episode_number}/videos")
    suspend fun retrieveTvEpisodeVideos(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int
    ): MovieVideoResponse

    @GET("search/tv")
    suspend fun retrieveTvSearchResult(
        @Query("query") query: String
    ): TvSearchResponse
}