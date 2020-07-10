package com.search.movie.app.feature.movie.data

import com.search.movie.app.framework.ext.empty
import java.io.Serializable

data class Movie(
    internal val id: String,
    internal val title: String,
    internal val year: String,
    internal val type: String,
    internal val poster: String
) : Serializable {

    internal var director: String = ""
    internal var released: String = ""
    internal var summary: String = ""
    internal var country: String = ""
    internal var isFavorite: Boolean = false

    companion object {
        fun empty() =
            Movie(String.empty(), String.empty(), String.empty(), String.empty(), String.empty())
    }
}
