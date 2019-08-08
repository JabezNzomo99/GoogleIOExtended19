package com.jabezmagomere.movies

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import kotlin.coroutines.ContinuationInterceptor

@ExperimentalCoroutinesApi
class CustomMainCoroutineRule : BeforeEachCallback,AfterEachCallback,TestCoroutineScope by TestCoroutineScope(){
    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()

    }


}