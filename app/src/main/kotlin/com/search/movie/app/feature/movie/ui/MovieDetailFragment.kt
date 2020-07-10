package com.search.movie.app.feature.movie.ui

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.search.movie.app.R
import com.search.movie.app.feature.movie.data.Movie
import com.search.movie.app.framework.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_detail_movie.*


class MovieDetailFragment : BaseFragment() {

    private lateinit var movie: Movie
    override fun layoutId(): Int = R.layout.fragment_detail_movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        movie = arguments!!.getSerializable("movie") as Movie
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView(view)
    }


    private fun initializeView(view: View) {

        Glide.with(this)
            .load(movie.poster)
            .centerCrop()
            .placeholder(R.drawable.ic_movie_placeholder)
            .error(R.drawable.ic_icon_delete)
            .into(detailMovieImageView)

        detailMovieNameTextView!!.text = movie.title
        detailMovieSummary!!.text = movie.summary
        detailMovieCountry!!.text = movie.country
        detailMovieRelease!!.text = movie.released
        detailType!!.text = movie.type

    }

}