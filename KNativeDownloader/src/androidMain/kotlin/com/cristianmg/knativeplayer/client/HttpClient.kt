package com.cristianmg.knativeplayer.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

actual fun getHttpClient(): HttpClient {
    return HttpClient(OkHttp.create {
        config {
            followRedirects(true)
        }
    })
}

