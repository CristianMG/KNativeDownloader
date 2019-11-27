package com.cristianmg.knativedownloader

import com.cristianmg.knativedownloader.client.HttpClient
import com.cristianmg.knativedownloader.data.KNativeDownloadRepository
import com.cristianmg.knativedownloader.model.FileDownload

/**
 * @property httpClient HttpClient ktor client to do downloads
 * @constructor
 */
class KNativeDownloader(private val httpClient: io.ktor.client.HttpClient = HttpClient.clientDefault()) {

    private val repository: KNativeDownloadRepository by lazy { KNativeDownloadRepository.instance }
    private val queue: DownloadQueue by lazy { DownloadQueue() }
    private val produceWorker: ProduceWorker by lazy { ProduceWorker(queue) }

    /**
     * Init producer which function is add to queue
     */
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


    fun downloadFile(url: String) {
        val newDownload = FileDownload(url)
        repository.insert(newDownload)
    }

}