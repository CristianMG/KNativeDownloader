package com.cristianmg.knativeplayer.engine

import com.cristianmg.knativeplayer.client.HttpClient

class DownloaderEngine {

    val httpClient: io.ktor.client.HttpClient by lazy { HttpClient.client() }


    suspend fun downloadFile(url: String) {

    }

}

expect fun downloadItem(downloadState: (DownloadState) -> Unit, url: String)