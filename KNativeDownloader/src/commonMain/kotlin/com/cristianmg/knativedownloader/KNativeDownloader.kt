package com.cristianmg.knativedownloader

import com.cristianmg.knativedownloader.client.HttpClient
import com.cristianmg.knativedownloader.engine.DownloadResult
import com.cristianmg.knativedownloader.engine.DownloaderEngine

class KNativeDownloader(
        private val httpClient: io.ktor.client.HttpClient = HttpClient.clientDefault()
) {

    private val engine: DownloaderEngine by lazy { DownloaderEngine(httpClient) }

    suspend fun addItemToQueue(url: String, callback: (result: DownloadResult) -> Unit) {
        engine.downloadFile(url, callback)
    }

}