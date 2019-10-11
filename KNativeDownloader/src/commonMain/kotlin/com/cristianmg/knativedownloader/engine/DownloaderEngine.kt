package com.cristianmg.knativedownloader.engine

import com.cristianmg.knativedownloader.engine.file.FileDownload
import com.cristianmg.knativedownloader.log.Logger
import io.ktor.client.call.call
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.contentLength
import io.ktor.http.isSuccess
import kotlinx.coroutines.io.ByteReadChannel

/**
 * @property httpClient HttpClient client ktor that we have to use for download
 * @property listener DownloaderEngineListener listener to report about download state
 * @constructor
 */
class DownloaderEngine(private val httpClient: io.ktor.client.HttpClient,
                       private val listener: DownloaderEngineListener) {

    /**
     * @param url String url file to download
     */
    suspend fun downloadFile(url: String) {
        try {
            val call = httpClient.call(HttpRequestBuilder()
                    .apply {
                        url(url)
                        method = HttpMethod.Get
                    }
            )
            if (!call.response.status.isSuccess()) {
                throw DownloaderException(call.response.toString())
            }

            val fileDownload = FileDownload(url, call.response.contentLength() ?: 0L)

            saveBufferStreamToFile(call.response.content, fileDownload) {
                Logger.d("Event byte read received state: $fileDownload")
            }

            listener.onDownloadFinish(DownloadResult.Success(fileDownload))
        } catch (exception: Exception) {
            listener.onDownloadFinish(DownloadResult.Failed(exception, exception.message))
        }
    }
}


/**
 * @param channel ByteReadChannel channel to save in file
 * @param fileDownload FileDownload file with information where byte read channel must be save
 */
expect suspend fun saveBufferStreamToFile(channel: ByteReadChannel, fileDownload: FileDownload, bytesRead: (fileDownload: FileDownload) -> Unit)
