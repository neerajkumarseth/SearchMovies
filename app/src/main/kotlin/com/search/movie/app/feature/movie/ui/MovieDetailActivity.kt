package com.search.movie.app.feature.movie.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.search.movie.app.framework.base.BaseActivity


class MovieDetailActivity : BaseActivity() {

    companion object {
        fun callerIntent(context: Context) = Intent(context, MovieDetailActivity::class.java)
    }

    override fun fragment(): MovieDetailFragment {

        val bundle = Bundle()
        bundle.putSerializable("movie", intent.getSerializableExtra("movie"))
        var detailFragment = MovieDetailFragment()
        detailFragment.arguments = bundle
        return detailFragment
    }

}