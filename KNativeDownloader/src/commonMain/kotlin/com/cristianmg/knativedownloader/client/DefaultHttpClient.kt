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

    fun clientDefault() = getDefaultHttpClient()

}


expect fun getDefaultHttpClient(): HttpClient
