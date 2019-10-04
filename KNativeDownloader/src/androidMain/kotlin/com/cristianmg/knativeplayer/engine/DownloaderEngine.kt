package com.cristianmg.knativeplayer.engine

import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.http.HttpMethod
import io.ktor.http.isSuccess
import io.ktor.util.cio.writeChannel
import kotlinx.coroutines.io.copyAndClose
import java.io.File
import java.net.URL

actual fun downloadItem(downloadState: (DownloadState) -> Unit, url: String): DownloadResult {
    return DownloadResult(true)
}


suspend fun HttpClient.getAsTempFile(url: String, callback: suspend (file: File) -> Unit) {
    val file = getAsTempFile(url)
    try {
        callback(file)
    } finally {
        file.delete()
    }
}

suspend fun HttpClient.getAsTempFile(url: String): File {
    val file = File.createTempFile("ktor", "http-client")
    val call = call {
        url { URL(url) }
        method = HttpMethod.Get
    }
    if (!call.response.status.isSuccess()) {
        throw DownloaderException(call.response.toString())
    }
    call.response.content.copyAndClose(file.writeChannel())
    return file
}

