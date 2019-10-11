package com.cristianmg.knativedownloader

import com.cristianmg.knativedownloader.client.HttpClient
import com.cristianmg.knativedownloader.engine.DownloadResult
import com.cristianmg.knativedownloader.engine.DownloaderEngine
import com.cristianmg.knativedownloader.engine.DownloaderEngineListener
import com.cristianmg.knativedownloader.engine.file.FileDownload
import kotlinx.coroutines.CoroutineScope
import kotlin.jvm.Synchronized

/**
 * @property httpClient HttpClient ktor client to do downloads
 * @property engine DownloaderEngine instance of engine
 * @constructor
 */
class KNativeDownloader(
        private val httpClient: io.ktor.client.HttpClient = HttpClient.clientDefault()
) : DownloaderEngineListener {

    private val engine: DownloaderEngine by lazy { DownloaderEngine(httpClient, this) }

    /**
     * @param url String
     */
    suspend fun addItemToQueue(url: String) {
        engine.downloadFile(url)
    }


    @Synchronized
    override fun onProgressDownloadChange(fileDownload: FileDownload) {

    }

    @Synchronized
    override fun onDownloadFinish(downloadResult: DownloadResult) {

    }

}