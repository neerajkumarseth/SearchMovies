package com.search.movie.app.framework.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.search.movie.app.feature.movie.model.FavoriteMovieModel
import com.search.movie.app.feature.movie.model.SearchMovieModel
import com.search.movie.app.framework.base.ViewModelFactory
import com.search.movie.app.framework.di.model.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchMovieModel::class)
    abstract fun bindsMoviesViewModel(searchMovieModel: SearchMovieModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteMovieModel::class)
    abstract fun bindsFavoriteMoviesViewModel(favoriteMovieModel: FavoriteMovieModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}