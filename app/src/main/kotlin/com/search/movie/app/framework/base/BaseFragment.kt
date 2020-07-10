package com.search.movie.app.framework.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.search.movie.app.framework.core.SearchMovieApp
import com.search.movie.app.framework.di.AppComponent
import me.samlss.broccoli.Broccoli
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    abstract fun layoutId(): Int
    val broccoli: Broccoli = Broccoli()

    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as SearchMovieApp).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(layoutId(), container, false)
    }

    open fun onBackPressed() {}

}