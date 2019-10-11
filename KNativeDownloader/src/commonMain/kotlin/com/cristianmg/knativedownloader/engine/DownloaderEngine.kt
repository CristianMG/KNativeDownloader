package com.cristianmg.knativedownloader.engine

import com.cristianmg.knativedownloader.engine.file.FileDownload
import io.ktor.client.call.call
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.isSuccess
import kotlinx.coroutines.io.ByteReadChannel

/**
 * @property httpClient HttpClient client ktor that we have to use for download
 * @constructor
 */
class DownloaderEngine(private val httpClient: io.ktor.client.HttpClient) {


    /**
     * @param url String url file to download
     * @param callback  (result: DownloadResult) -> Unit callback which engine report about download state
     */
    suspend fun downloadFile(url: String, callback: (result: DownloadResult) -> Unit) {
        try {
            val fileDownload = FileDownload(url)

            val call = httpClient.call(HttpRequestBuilder()
                    .apply {
                        url(fileDownload.url)
                        method = HttpMethod.Get
                    }
            )
            if (!call.response.status.isSuccess()) {
                throw DownloaderException(call.response.toString())
            }

            saveBufferStreamToFile(call.response.content, fileDownload)
            callback(DownloadResult.Success(fileDownload))
        } catch (exception: Exception) {
            callback(DownloadResult.Failed(exception, exception.message))
            return
        }
    }
}


/**
 * @param channel ByteReadChannel channel to save in file
 * @param fileDownload FileDownload file with information where byte read channel must be save
 */
expect suspend fun saveBufferStreamToFile(channel: ByteReadChannel, fileDownload: FileDownload)
