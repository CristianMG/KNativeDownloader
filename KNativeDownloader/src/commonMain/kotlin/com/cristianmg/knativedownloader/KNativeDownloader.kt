package com.cristianmg.knativedownloader

import com.cristianmg.knativedownloader.client.HttpClient
import com.cristianmg.knativedownloader.data.KNativeDownloadRepository
import com.cristianmg.knativedownloader.model.FileDownload

/**
 * @property httpClient HttpClient ktor client to do downloads
 * @property engine DownloaderEngine instance of engine
 * @constructor
 */
class KNativeDownloader(
        private val httpClient: io.ktor.client.HttpClient = HttpClient.clientDefault()
) {


    private val repository: KNativeDownloadRepository by lazy { KNativeDownloadRepository.instance }
    private val queue: DownloadQueue by lazy { DownloadQueue() }
    private val produceWorker: ProduceWorker by lazy { ProduceWorker(this, queue) }

    fun init() {
        produceWorker.initProducer()
    }

    /**
     * Stop producer and stop all downloads
     */
    fun stop() {
        produceWorker.closeProducer()
        queue.cancelAll()
    }



    fun putPending(url: String) {
        val newDownload = FileDownload(url)
        repository.insertDownload(newDownload)
    }

    companion object {
        /**
         * Number of downloads that will do at the same time
         */
        const val PARALLEL_DOWNLOADS_DEFAULT: Long = 1
    }
}