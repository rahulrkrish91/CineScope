package com.malabar.malabarmoviesapp

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.request.crossfade
import com.malabar.core.AnalyticsHelper
import com.malabar.malabarmoviesapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MyApp : Application(), SingletonImageLoader.Factory {

    override fun onCreate() {
        super.onCreate()
        AnalyticsHelper.init(applicationContext)
        startKoin {
            androidLogger()
            androidContext(applicationContext)

            val moduleList: List<Module> = with(mutableListOf<Module>()) {
                addAll(appModule)
                this
            }

            modules(moduleList)
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(true)
            .build()
    }
}