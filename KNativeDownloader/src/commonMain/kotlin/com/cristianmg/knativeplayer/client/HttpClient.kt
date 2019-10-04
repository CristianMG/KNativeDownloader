package com.cristianmg.knativeplayer.client

import com.cristianmg.knativeplayer.log.Logger
import io.ktor.client.HttpClient

object HttpClient {
    init {
        Logger.d("Initializing with object: $this")
    }

    fun client() = getHttpClient()

}


expect fun getHttpClient(): HttpClient
