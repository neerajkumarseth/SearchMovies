package com.search.movie.app.framework.di

import android.app.Application
import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.search.movie.app.feature.movie.repo.DataProvider
import com.search.movie.app.feature.movie.repo.DataProviderImpl
import com.search.movie.app.framework.core.SearchMovieApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: SearchMovieApp) {
    @Provides
    @Singleton
    fun provideAppContext(): Context = application

    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    fun provideMovieSource(dataSourceImpl: DataProviderImpl): DataProvider = dataSourceImpl

    @Provides
    @Singleton
    fun providesRequestQueue(application: Application): RequestQueue =
        Volley.newRequestQueue(application)
}