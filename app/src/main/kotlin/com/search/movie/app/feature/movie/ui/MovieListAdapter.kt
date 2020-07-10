package com.search.movie.app.feature.movie.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.search.movie.app.R
import com.search.movie.app.feature.movie.data.Movie
import com.search.movie.app.framework.ext.inflate
import com.search.movie.app.framework.navigation.Navigator
import kotlinx.android.synthetic.main.item_movie.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class MovieListAdapter @Inject constructor() :
    RecyclerView.Adapter<MovieListAdapter.ItemViewHolder>() {

    internal val favoriteList: ArrayList<Movie> = ArrayList()

    @Inject
    internal lateinit var navigator: Navigator

    // delegates are awesome in kotlin, please see : https://proandroiddev.com/the-magic-in-kotlin-delegates-377d27a7b531
    internal var data: List<Movie> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(parent.inflate(R.layout.item_movie))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // bind data to view
        fun bind(movie: Movie) {
            itemView.movieNameTextView.text = movie.title
            itemView.movieDirectorNameTextView.text = movie.director
            itemView.movieDetailNameTextView.text = movie.summary
            itemView.yearTextView.text = movie.year
            itemView.favoriteButton.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) favoriteList.add(movie)
                else favoriteList.remove(movie)
            }
            if (movie.poster != null) {
                Glide.with(itemView)
                    .load(movie.poster)
                    .centerCrop()
                    .placeholder(R.drawable.ic_movie_placeholder)
                    .error(R.drawable.ic_icon_delete)
                    .into(itemView.movieImageView)
            }

            itemView.setOnClickListener {
                navigator.showDetailScreen(it.context, movie)
            }
        }
    }
}