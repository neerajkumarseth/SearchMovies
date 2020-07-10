package com.search.movie.app.framework.base

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.search.movie.app.R
import com.search.movie.app.framework.ext.addFragment

abstract class BaseActivity : AppCompatActivity() {

    // attach fragment as well if it is created first time
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.fullScreen()
        this.setContentView(R.layout.activity_base)
        addFragment(savedInstanceState)
    }

    private fun fullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    // other activity needs to override it
    abstract fun fragment(): BaseFragment

    private fun addFragment(savedInstanceState: Bundle?) {
        savedInstanceState ?: supportFragmentManager.addFragment {
            add(R.id.fragmentContainer, fragment())
        }
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(R.id.fragmentContainer) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }
}