package com.cristianmg.knativedownloader.engine

import com.cristianmg.knativedownloader.log.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.http.HttpMethod
import io.ktor.http.isSuccess
import io.ktor.util.cio.writeChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.io.copyAndClose
import java.io.File
import java.net.URL

actual suspend fun downloadItem(downloadState: (DownloadState) -> Unit, url: String, httpClient: HttpClient): DownloadResult {
    val file = httpClient.getAsTempFile(url)
    return DownloadResult(file != null)
}


suspend fun HttpClient.getAsTempFile(url: String): File? {
    return try {
        val file = File.createTempFile("ktor", "http-clientDefault")
        val call = call {
            url { URL(url) }
            method = HttpMethod.Get
        }
        if (!call.response.status.isSuccess()) {
            throw DownloaderException(call.response.toString())
        }
        call.response.content.copyAndClose(file.writeChannel())
        file
    } catch (e: Exception) {
        Logger.e("Error when try to download file")
        null
    }
}

actual fun getCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)