package com.search.movie.app.feature.movie.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.search.movie.app.R
import com.search.movie.app.feature.movie.data.Movie
import com.search.movie.app.feature.movie.model.SearchMovieModel
import com.search.movie.app.framework.base.BaseFragment
import com.search.movie.app.framework.base.EmptyListObserver
import com.search.movie.app.framework.ext.toInvisible
import com.search.movie.app.framework.ext.toVisible
import com.search.movie.app.framework.ext.toast
import com.search.movie.app.framework.ext.viewModel
import com.search.movie.app.framework.navigation.Navigator
import kotlinx.android.synthetic.main.fragment_search_movie.*
import kotlinx.android.synthetic.main.item_movie.*
import javax.inject.Inject


class MovieListFragment : BaseFragment() {

    private lateinit var viewModel: SearchMovieModel

    @Inject
    internal lateinit var movieListAdapter: MovieListAdapter

    @Inject
    internal lateinit var navigator: Navigator


    override fun layoutId(): Int = R.layout.fragment_search_movie

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
        // add default place holders
        broccoli.addPlaceholders(
            listItemMovieLayout,
            R.id.movieImageView,
            R.id.movieDetailNameTextView
        )
        // init list view
        movieList.adapter = movieListAdapter
        movieList!!.layoutManager = LinearLayoutManager(context)
        movieList!!.addItemDecoration(DividerItemDecoration(movieList!!.context, 0))
        movieList.toInvisible()

        val observer = EmptyListObserver(movieList, emptyViewTextView)
        movieListAdapter.registerAdapterDataObserver(observer)

        searchButton.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view?.toast(getString(R.string.please_wait))
                loadMovies(searchButton.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

    }

    private fun loadMovies(searchText: String) {
        movieList.toVisible()
        viewModel.searchMovie(searchText).observe(this, Observer {
            renderMovieList(it)
        })
    }

    // this solution is not scalable, db would have better solution if not the best
    private fun saveFavoriteList(list: ArrayList<Movie>) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("FavoriteMovieList", json)
        editor.apply()
    }

    // save when page is hidden
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isVisibleToUser &&
            this::movieListAdapter.isInitialized &&
            movieListAdapter.favoriteList.size != 0
        ) {
            saveFavoriteList(movieListAdapter.favoriteList)
        }
    }
}