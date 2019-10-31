package com.cristianmg.knativedownloader.client

import com.cristianmg.knativedownloader.log.Logger
import io.ktor.client.HttpClient

/**
 * Singleton for initializing httpclient
 */
object HttpClient {

    init {
        Logger.d("Initializing with object: $this")
    }

    /***
     * Singleton default http client
     * @return HttpClient
     */
    fun clientDefault(): HttpClient = getDefaultHttpClient()

}

/**
 * Expect function each platform must return its implementation
 * @return HttpClient
 */
expect fun getDefaultHttpClient(): HttpClient
