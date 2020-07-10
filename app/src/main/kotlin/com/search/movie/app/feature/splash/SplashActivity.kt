package com.search.movie.app.feature.splash

import android.os.Bundle
import android.os.Handler
import com.search.movie.app.framework.core.SearchMovieApp
import com.search.movie.app.framework.base.BaseActivity
import com.search.movie.app.framework.base.BaseFragment
import com.search.movie.app.framework.di.AppComponent
import com.search.movie.app.framework.navigation.Navigator
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    private val appComponent: AppComponent by lazy {
        (application as SearchMovieApp).appComponent
    }

    @Inject
    internal lateinit var navigator: Navigator

    override fun fragment(): BaseFragment {
        return SplashFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        Handler().postDelayed({
            navigator.showSearchMovieScreen(this)
            finish()
        }, 1500)
    }
}