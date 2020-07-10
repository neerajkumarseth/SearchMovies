package com.search.movie.app.feature.movie.repo

import androidx.lifecycle.MutableLiveData
import com.search.movie.app.feature.movie.data.Movie

interface DataProvider {
    fun searchMovies(text: String): MutableLiveData<List<Movie>>
    fun loadFavoriteMovies(): MutableLiveData<List<Movie>>
}