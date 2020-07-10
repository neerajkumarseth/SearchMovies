package com.search.movie.app.feature.movie.model

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.search.movie.app.feature.movie.data.Movie
import com.search.movie.app.feature.movie.repo.DataProviderImpl
import com.search.movie.app.framework.base.BaseViewModel
import javax.inject.Inject

class SearchMovieModel
@Inject constructor(private val dataProviderImpl: DataProviderImpl) : BaseViewModel() {
    var movies: MediatorLiveData<List<Movie>> = MediatorLiveData()
    fun searchMovie(searchText: String): MutableLiveData<List<Movie>> =
        dataProviderImpl.searchMovies(searchText)
}