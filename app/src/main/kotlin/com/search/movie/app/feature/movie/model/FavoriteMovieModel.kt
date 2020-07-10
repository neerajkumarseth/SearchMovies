package com.search.movie.app.feature.movie.model

import androidx.lifecycle.MutableLiveData
import com.search.movie.app.feature.movie.data.Movie
import com.search.movie.app.feature.movie.repo.DataProviderImpl
import com.search.movie.app.framework.base.BaseViewModel
import javax.inject.Inject

class FavoriteMovieModel
@Inject constructor(private val dataProviderImpl: DataProviderImpl) : BaseViewModel() {
    fun loadMovies(): MutableLiveData<List<Movie>> = dataProviderImpl.loadFavoriteMovies()
}