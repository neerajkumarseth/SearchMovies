package com.search.movie.app.framework.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    var failure = MutableLiveData<Any>()

}