package com.search.movie.app.framework.di

import com.search.movie.app.feature.movie.ui.FavoriteListFragment
import com.search.movie.app.feature.movie.ui.MovieDetailFragment
import com.search.movie.app.feature.movie.ui.MovieListFragment
import com.search.movie.app.feature.splash.SplashActivity
import com.search.movie.app.framework.core.SearchMovieApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(application: SearchMovieApp)
    fun inject(splashActivity: SplashActivity)
    fun inject(fragment: MovieListFragment)
    fun inject(fragment: MovieDetailFragment)
    fun inject(fragment: FavoriteListFragment)
}