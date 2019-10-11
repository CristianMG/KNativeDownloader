package com.cristianmg.knativedownloader.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

actual fun getDefaultHttpClient(): HttpClient {
    return HttpClient(OkHttp.create {
        config {
            followRedirects(true)
        }
    })
}

