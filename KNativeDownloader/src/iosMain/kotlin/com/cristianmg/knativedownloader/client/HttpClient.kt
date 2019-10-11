package com.cristianmg.knativedownloader.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios

actual fun getDefaultHttpClient(): HttpClient {
    return HttpClient(Ios.create {})
}

