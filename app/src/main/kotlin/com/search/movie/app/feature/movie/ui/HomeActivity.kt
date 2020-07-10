package com.search.movie.app.feature.movie.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.search.movie.app.R
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : AppCompatActivity() {

    companion object {
        fun callerIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewPager()
    }

    private fun initViewPager() {
        val tabsAdapter = TabsAdapter(this, supportFragmentManager)
        viewPager.adapter = tabsAdapter
        tabs.setupWithViewPager(viewPager)
        val tabListener: OnTabSelectedListener = object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        }
        tabs.addOnTabSelectedListener(tabListener)

        TAB_ICONS.forEachIndexed { index, icon -> tabs.getTabAt(index)?.icon = getDrawable(icon) }
    }
}
