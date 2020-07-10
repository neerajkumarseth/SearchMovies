package com.search.movie.app.framework.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toInvisible() {
    this.visibility = View.GONE
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun View.toast(message: CharSequence) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
