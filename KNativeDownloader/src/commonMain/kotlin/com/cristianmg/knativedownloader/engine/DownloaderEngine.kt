package com.cristianmg.knativedownloader.engine

import com.cristianmg.knativedownloader.getCurrentThread
import com.cristianmg.knativedownloader.model.FileDownload
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
 * @property listener DownloaderEngineListener? listener to report about general download can be null
 * @constructor
 */
class DownloaderEngine(private val httpClient: io.ktor.client.HttpClient,
                       private val listener: DownloaderEngineListener? = null) {

    /**
     * @param url String url file to download
     */
    suspend fun downloadFile(download: FileDownload): DownloadResult {
        try {
            Logger.d("Download request in thread ${getCurrentThread()}")
            val call = httpClient.call(HttpRequestBuilder()
                    .apply {
                        url(download.url)
                        method = HttpMethod.Get
                    }
            )
            if (!call.response.status.isSuccess()) {
                throw DownloaderException(call.response.toString())
            }

            download.sizeFile = call.response.contentLength() ?: 0L

            val filePath = saveBufferStreamToFile(call.response.content, download) {
                Logger.d("download: ${download.url} byteRad:$it")
            }

            download.downloadedPath = filePath

            listener?.onDownloadFinish(DownloadResult.Success(download))
            return DownloadResult.Success(download)
        } catch (exception: Exception) {
            val downloadFailure = DownloadResult.Failed(download,exception, exception.message)
            listener?.onDownloadFinish(downloadFailure)
            return downloadFailure
        }
    }
}


/**
 * @param channel ByteReadChannel channel to save in file
 * @param fileDownload FileDownload file with information where byte read channel must be save
 */
expect suspend fun saveBufferStreamToFile(channel: ByteReadChannel, fileDownload: FileDownload, bytesRead: (byteDownloaded: Long) -> Unit): String
