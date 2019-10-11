package com.cristianmg.knativedownloader.engine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

actual suspend fun downloadItem(downloadState: (DownloadState) -> Unit, url: String, httpClient: io.ktor.client.HttpClient): DownloadResult {
    return DownloadResult(true)
}


actual fun getCoroutineScope(): CoroutineScope  = CoroutineScope(Dispatchers.Default)