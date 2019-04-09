package com.jabezmagomere.movies.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel:ViewModel(),CoroutineScope{
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = (Dispatchers.Main + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}