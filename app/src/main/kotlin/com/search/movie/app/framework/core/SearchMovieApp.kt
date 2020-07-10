package com.search.movie.app.framework.core

import android.app.Application
import com.search.movie.app.BuildConfig
import com.search.movie.app.framework.di.AppComponent
import com.search.movie.app.framework.di.AppModule
import com.search.movie.app.framework.di.DaggerAppComponent
import com.search.movie.app.framework.log.Logger
import com.squareup.leakcanary.LeakCanary


class SearchMovieApp : Application() {

    internal val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Logger.i(this::class, "Search Movies Application is created!")
        this.injectMembers()
        this.initLeakDetection()
    }

    private fun injectMembers() = appComponent.inject(this)

    // useful for memory leak detection
    private fun initLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Logger.i(
            this::class,
            "Search Movies Application onTrimMemory () is called with level : $level"
        )
    }
}