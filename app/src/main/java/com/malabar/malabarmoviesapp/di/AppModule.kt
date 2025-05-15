package com.malabar.malabarmoviesapp.di

import android.app.Activity
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.malabar.core.AppConstants.Companion.MOVIE_BASE_URL
import com.malabar.core.AppConstants.Companion.MOVIE_HEADER
import com.malabar.core.db.AppDatabase
import com.malabar.core.db.dao.ApiKeyDao
import com.malabar.core.db.interactors.DeleteApiKeyUseCase
import com.malabar.core.db.interactors.GetApiKeyUseCase
import com.malabar.core.db.interactors.InsertApiKeyUseCase
import com.malabar.core.db.repository.ApiKeyRepository
import com.malabar.malabarmoviesapp.api.MovieApi
import com.malabar.malabarmoviesapp.domain.data.app_update.InAppUpdateManager
import com.malabar.malabarmoviesapp.domain.data.app_update.InAppUpdateManagerImpl
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieCastCreditsUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieCastInfoUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieCreditsUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieDetailsUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieImagesUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieReviewsUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieSearchUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetMovieVideoUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetMultiSearchUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetNowPlayingMovieUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetPopularMovieUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetRecommendedMovieUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetSimilarMoviesUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetTopRatedMovieUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetTrendingMoviesUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetTrendingPersonUseCase
import com.malabar.malabarmoviesapp.domain.interactors.GetUpcomingMovieUseCase
import com.malabar.malabarmoviesapp.domain.interactors.auth.SignInWithGoogleUseCase
import com.malabar.malabarmoviesapp.domain.interactors.in_app_update.GetCheckAndStartUpdateUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetPopularTvShowUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTopRatedTvShowUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTrendingTvShowsUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTvAiringTodayUseCase
import com.malabar.malabarmoviesapp.domain.interactors.tv.GetTvCastCreditsUseCase
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
import com.malabar.malabarmoviesapp.domain.repository.MovieCastCreditsRepository
import com.malabar.malabarmoviesapp.domain.repository.MovieCastRepository
import com.malabar.malabarmoviesapp.domain.repository.MovieDetailsRepository
import com.malabar.malabarmoviesapp.domain.repository.MovieNowPlayingRepository
import com.malabar.malabarmoviesapp.domain.repository.MoviePopularRepository
import com.malabar.malabarmoviesapp.domain.repository.MovieRecommendedRepository
import com.malabar.malabarmoviesapp.domain.repository.MovieReviewRepository
import com.malabar.malabarmoviesapp.domain.repository.MovieSearchRepository
import com.malabar.malabarmoviesapp.domain.repository.MovieTopRatedRepository
import com.malabar.malabarmoviesapp.domain.repository.MovieTrendingRepository
import com.malabar.malabarmoviesapp.domain.repository.MovieUpcomingRepository
import com.malabar.malabarmoviesapp.domain.repository.auth.AuthRepository
import com.malabar.malabarmoviesapp.domain.repository.auth.AuthRepositoryImpl
import com.malabar.malabarmoviesapp.domain.repository.auth.TvRecommendationsRepository
import com.malabar.malabarmoviesapp.domain.repository.tv.TvAiringTodayRepository
import com.malabar.malabarmoviesapp.domain.repository.tv.TvCastCreditsRepository
import com.malabar.malabarmoviesapp.domain.repository.tv.TvCreditsRepository
import com.malabar.malabarmoviesapp.domain.repository.tv.TvEpisodeDetailsRepository
import com.malabar.malabarmoviesapp.domain.repository.tv.TvOnTheAirRepository
import com.malabar.malabarmoviesapp.domain.repository.tv.TvPopularRepository
import com.malabar.malabarmoviesapp.domain.repository.tv.TvSearchRepository
import com.malabar.malabarmoviesapp.domain.repository.tv.TvSeasonDetailsRepository
import com.malabar.malabarmoviesapp.domain.repository.tv.TvSeriesDetailsRepository
import com.malabar.malabarmoviesapp.domain.repository.tv.TvSeriesVideoRepository
import com.malabar.malabarmoviesapp.domain.repository.tv.TvTopRatedRepository
import com.malabar.malabarmoviesapp.domain.repository.tv.TvTrendingRepository
import com.malabar.malabarmoviesapp.ui.InAppUpdateViewModel
import com.malabar.malabarmoviesapp.ui.MovieDetailsViewModel
import com.malabar.malabarmoviesapp.ui.MovieSearchViewModel
import com.malabar.malabarmoviesapp.ui.MovieViewModel
import com.malabar.malabarmoviesapp.ui.security.AuthViewModel
import com.malabar.malabarmoviesapp.ui.tv.TvViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.dsl.single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun provideGsonConverter(): GsonConverterFactory {
    return GsonConverterFactory.create()
}

fun provideOkHttpClient(): OkHttpClient {
    val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $MOVIE_HEADER")
            .build()
        chain.proceed(newRequest)

    }
    return OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()
}

fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(MOVIE_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

fun provideMovieApi(retrofit: Retrofit): MovieApi {
    return retrofit.create(MovieApi::class.java)
}

val networkModule = module {
    single { provideGsonConverter() }
    single { provideOkHttpClient() }
    single {
        provideRetrofit(
            okHttpClient = get(),
            gsonConverterFactory = get()
        )
    }
    single { provideMovieApi(retrofit = get()) }
}

val repositoryModule = module {
    single {
        Gson()
    }
    singleOf(::MovieNowPlayingRepository)
    singleOf(::MoviePopularRepository)
    singleOf(::MovieTopRatedRepository)
    singleOf(::MovieUpcomingRepository)
    singleOf(::MovieDetailsRepository)
    singleOf(::MovieCastRepository)
    singleOf(::MovieRecommendedRepository)
    single {
        MovieSearchRepository(get(), get())
    }
    singleOf(::MovieTrendingRepository)
    singleOf(::MovieReviewRepository)
    singleOf(::MovieCastCreditsRepository)

    // Tv
    singleOf(::TvAiringTodayRepository)
    singleOf(::TvOnTheAirRepository)
    singleOf(::TvPopularRepository)
    singleOf(::TvTopRatedRepository)
    singleOf(::TvSeriesDetailsRepository)
    singleOf(::TvSeasonDetailsRepository)
    singleOf(::TvCreditsRepository)
    singleOf(::TvEpisodeDetailsRepository)
    singleOf(::TvTrendingRepository)
    singleOf(::TvRecommendationsRepository)
    singleOf(::TvSeriesVideoRepository)
    singleOf(::TvSearchRepository)
    singleOf(::TvCastCreditsRepository)
}

val interactorModule = module {
    factoryOf(::GetNowPlayingMovieUseCase)
    factoryOf(::GetPopularMovieUseCase)
    factoryOf(::GetTopRatedMovieUseCase)
    factoryOf(::GetUpcomingMovieUseCase)
    factoryOf(::GetMovieDetailsUseCase)
    factoryOf(::GetMovieCreditsUseCase)
    factoryOf(::GetMovieCastInfoUseCase)
    factoryOf(::GetRecommendedMovieUseCase)
    factoryOf(::GetSimilarMoviesUseCase)
    factoryOf(::GetMovieImagesUseCase)
    factoryOf(::GetMovieVideoUseCase)
    factoryOf(::GetMovieSearchUseCase)
    factoryOf(::GetTrendingMoviesUseCase)
    factoryOf(::GetMovieReviewsUseCase)
    factoryOf(::GetTrendingPersonUseCase)
    factoryOf(::GetMultiSearchUseCase)
    factoryOf(::GetMovieCastCreditsUseCase)

    // Tv
    factoryOf(::GetTvAiringTodayUseCase)
    factoryOf(::GetTvOnTheAirUseCase)
    factoryOf(::GetPopularTvShowUseCase)
    factoryOf(::GetTopRatedTvShowUseCase)
    factoryOf(::GetTvSeriesDetailsUseCase)
    factoryOf(::GetTvSeasonDetailsUseCase)
    factoryOf(::GetTvCreditsUseCase)
    factoryOf(::GetTvEpisodeDetailsUseCase)
    factoryOf(::GetTrendingTvShowsUseCase)
    factoryOf(::GetTvRecommendationsUseCase)
    factoryOf(::GetTvSeriesVideoUseCase)
    factoryOf(::GetTvSeasonVideoUseCase)
    factoryOf(::GetTvEpisodeVideoUseCase)
    factoryOf(::GetTvSearchResultUseCase)
    factoryOf(::GetTvCastCreditsUseCase)

    factory<InAppUpdateManager> { (activity: Activity) ->
        InAppUpdateManagerImpl(activity)
    }

    // Provide use case
    factory { (activity: Activity) ->
        GetCheckAndStartUpdateUseCase(get { parametersOf(activity) })
    }
}

val authModule = module {
    single { FirebaseAuth.getInstance() }
    single { CredentialManager.create(get<Context>()) }
    viewModel { AuthViewModel(get(), get(), get()) }
}

val viewModelModule = module {
    viewModelOf(::MovieViewModel)
    viewModelOf(::MovieDetailsViewModel)
    viewModelOf(::MovieSearchViewModel)

    // TV
    viewModelOf(::TvViewModel)

    viewModel { (activity: Activity) ->
        InAppUpdateViewModel(get { parametersOf(activity) })
    }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "cine_scope_db"
        )
    }

    single<ApiKeyDao> {
        get<AppDatabase>().apiKeyDao()
    }

    singleOf(::ApiKeyRepository)

    factoryOf(::InsertApiKeyUseCase)
    factoryOf(::GetApiKeyUseCase)
    factoryOf(::DeleteApiKeyUseCase)
}

val appModule: List<Module> = listOf(
    authModule,
    networkModule,
    repositoryModule,
    interactorModule,
    viewModelModule,
    databaseModule
)