package com.search.movie.app.framework.navigation

import android.content.Context
import androidx.annotation.NonNull
import com.search.movie.app.feature.movie.data.Movie
import com.search.movie.app.feature.movie.ui.HomeActivity
import com.search.movie.app.feature.movie.ui.MovieDetailActivity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class Navigator @Inject constructor() {

    fun showSearchMovieScreen(@NonNull context: Context) {
        context.startActivity(HomeActivity.callerIntent(context))
    }

    fun showDetailScreen(@NonNull context: Context, @NonNull movie: Movie) {
        context.startActivity(MovieDetailActivity.callerIntent(context).putExtra("movie", movie))
    }
}


