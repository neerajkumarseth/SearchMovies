package com.search.movie.app.feature.movie.repo

import android.content.Context
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.search.movie.app.R
import com.search.movie.app.feature.movie.data.Movie
import com.search.movie.app.framework.log.Logger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.reflect.Type
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject
import kotlin.concurrent.withLock


class DataProviderImpl @Inject constructor(
    private val context: Context,
    private val requestQueue: RequestQueue
) : DataProvider {

    private val API_KEY: String by lazy { context.getString(R.string.developer_api_key) }
    private val moviesResult: MediatorLiveData<List<Movie>> = MediatorLiveData()
    private val favoriteMovies: MediatorLiveData<List<Movie>> = MediatorLiveData()

    // just did not want to use Rx for just combining requests, instead use old MUTEX friend
    private val lock = ReentrantLock()
    private val mutex = lock.newCondition()

    companion object {
        private const val MEDIA_TYPE_SONGS = "movie"
        private const val BASE_URL = "https://www.omdbapi.com/"
    }

    override fun loadFavoriteMovies(): MediatorLiveData<List<Movie>> {
        doAsync {
            var listOfMovies = getFavoriteList(context)
            uiThread {
                favoriteMovies.value = listOfMovies
            }
        }
        return favoriteMovies
    }

    private fun getFavoriteList(context: Context): ArrayList<Movie> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json = prefs.getString("FavoriteMovieList", null)
        val type: Type = object : TypeToken<ArrayList<Movie>>() {}.type
        return gson.fromJson(json, type)
    }

    override fun searchMovies(text: String): MediatorLiveData<List<Movie>> {
        val url = "$BASE_URL?s=$text&apikey=$API_KEY&type=$MEDIA_TYPE_SONGS"
        Logger.i(this::class, "Search keyword is : $text and request URL is $url")

        val request = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                doAsync {
                    val jsonObject = JsonParser().parse(response).asJsonObject
                    val isSuccessful = jsonObject.get("Response").asBoolean
                    if (isSuccessful) {
                        val movies = jsonObject.get("Search").asJsonArray ?: JsonArray()
                        val listOfMovies = ArrayList<Movie>()
                        movies.forEach {
                            val movie = it as JsonObject
                            val id = movie.get("imdbID").asString
                            val title = movie.get("Title").asString
                            val year = movie.get("Year").asString
                            val type = movie.get("Type").asString
                            val poster = movie.get("Poster").asString

                            val moviePojo = Movie(id, title, year, type, poster)
                            //TODO better solution would have been call this method after all results are passed to View Layer
                            // for better/faster user experience
                            //fetchMovieDetailById(moviePojo)
                            //startProcessing()
                            listOfMovies.add(moviePojo)
                        }

                        uiThread {
                            moviesResult.value = listOfMovies
                        }
                    } else {
                        val errorMessage = jsonObject.get("Error").asString
                        uiThread {
                            // TODO nasty way of doing it, it must not be here, should be moved to view layer
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }

                }
            },
            Response.ErrorListener {
                doAsync {
                    uiThread {
                        // TODO nasty way of doing it, it must not be here, should be moved to view layer
                        Toast.makeText(
                            context,
                            context.getString(R.string.error),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }) {}
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        request.setShouldCache(false)
        requestQueue.add(request)

        return moviesResult
    }

    /**
     * Blocks current thread.
     */
    private fun startProcessing() {
        lock.withLock {
            mutex.await()
        }
    }

    /**
     * Unblocks threads in wait.
     */
    private fun doneProcessing() {
        lock.withLock {
            mutex.signalAll()
        }
    }

    private fun fetchMovieDetailById(movie: Movie) {
        val url = "$BASE_URL?i=${movie.id}&apikey=$API_KEY"
        Logger.i(this::class, "This request is for movie : ${movie.title} and request URL is $url")

        val request = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                doAsync {
                    val jsonObject = JsonParser().parse(response).asJsonObject
                    val isSuccessful = jsonObject.get("Response").asBoolean
                    if (isSuccessful) {
                        movie.released = jsonObject.get("Released").asString
                        movie.summary = jsonObject.get("Plot").asString
                        movie.director = jsonObject.get("Director").asString
                        movie.country = jsonObject.get("Country").asString
                    }
                    doneProcessing()
                }
            },
            Response.ErrorListener {
                doAsync {
                    doneProcessing()
                    uiThread { }
                }
            }) {}
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        request.setShouldCache(false)
        requestQueue.add(request)
    }
}