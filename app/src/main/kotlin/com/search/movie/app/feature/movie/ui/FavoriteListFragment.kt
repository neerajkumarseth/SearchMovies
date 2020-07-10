package com.search.movie.app.feature.movie.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.search.movie.app.R
import com.search.movie.app.feature.movie.data.Movie
import com.search.movie.app.feature.movie.model.FavoriteMovieModel
import com.search.movie.app.framework.base.BaseFragment
import com.search.movie.app.framework.ext.toInvisible
import com.search.movie.app.framework.ext.toVisible
import com.search.movie.app.framework.ext.viewModel
import com.search.movie.app.framework.navigation.Navigator
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import javax.inject.Inject


class FavoriteListFragment : BaseFragment() {

    private lateinit var viewModel: FavoriteMovieModel

    @Inject
    internal lateinit var movieListAdapter: MovieListAdapter

    @Inject
    internal lateinit var navigator: Navigator


    override fun layoutId(): Int = R.layout.fragment_favorite_movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        viewModel = this.viewModel(viewModelFactory) {}
    }

    private fun renderMovieList(movies: List<Movie>) {
        movieListAdapter.data = movies.orEmpty()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }


    private fun initializeView() {
        // init list view
        favoriteMovieList!!.adapter = movieListAdapter
        favoriteMovieList!!.layoutManager = LinearLayoutManager(context)
        favoriteMovieList!!.addItemDecoration(DividerItemDecoration(favoriteMovieList!!.context, 0))
        favoriteMovieList.toInvisible()
    }

    private fun loadMovies() {
        favoriteMovieList.toVisible()
        viewModel.loadMovies().observe(this, Observer {
            renderMovieList(it)
        })
    }


    // load when page is shown
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            loadMovies()
        }
    }
}