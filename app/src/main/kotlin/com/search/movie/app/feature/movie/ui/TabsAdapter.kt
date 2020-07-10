package com.search.movie.app.feature.movie.ui

import android.content.Context

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.search.movie.app.R


private val TAB_TITLES = arrayOf(
    R.string.search_movies,
    R.string.favorite_movies
)

val TAB_ICONS = arrayOf(
    R.drawable.movie,
    R.drawable.favorite
)

class TabsAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MovieListFragment()
            1 -> FavoriteListFragment()
            else -> MovieListFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }

}