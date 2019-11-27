package com.cristianmg.knativedownloader

import com.cristianmg.knativedownloader.client.HttpClient
import com.cristianmg.knativedownloader.data.KNativeDownloadRepository
import com.cristianmg.knativedownloader.engine.DownloadResult
import com.cristianmg.knativedownloader.engine.DownloaderEngine
import com.cristianmg.knativedownloader.engine.DownloaderEngineListener
import com.cristianmg.knativedownloader.model.DownloadJob
import com.cristianmg.knativedownloader.model.DownloadState
import com.cristianmg.knativedownloader.model.FileDownload
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel

/**
 *
 * @property httpClient HttpClient
 * @property repository KNativeDownloadRepository
 * @property jobs MutableList<DownloadJob>
 * @property engine DownloaderEngine
 * @property scope CoroutineScope
 * @constructor
 */
class DownloadQueue(
        private val httpClient: io.ktor.client.HttpClient = HttpClient.clientDefault()
) : DownloaderEngineListener {

    private val repository: KNativeDownloadRepository by lazy { KNativeDownloadRepository.instance }
    private var jobs: MutableList<DownloadJob> = mutableListOf()
    private val engine: DownloaderEngine by lazy { DownloaderEngine(httpClient, this) }
    private val scope: CoroutineScope by lazy { getCoroutineScope() }

    /**
     * Check if the queue is busy
     * @return Boolean
     */
    fun isBusy(): Boolean =
            jobs.size > 10

    /**
     * Stop download if it's started and remove from queue
     * @param downloadJob String url which we want remove and stop from downloader
     */
    fun cancel(downloadJob: DownloadJob) {
        if (downloadJob.job.isActive)
            downloadJob.job.cancel("Download cance by client")
        jobs.remove(downloadJob)
    }

    /**
     * Stop all downloads and clear queue
     */
    fun cancelAll() {
        jobs.removeAll {
            repository.updateStateDownload(it.fileDownload.uuid, DownloadState.PENDING)
            it.job.cancel("Cancel by client")
            true
        }
    }

    /**
     * Search the next item and add to job
     */
    fun addNextItemToQueue() {
        repository.getNextQueueItem()?.let {
            val worker = scope.async { engine.downloadFile(it) }
            jobs.add(DownloadJob(it, worker))
            worker.start()
        }
    }

    override fun onProgressDownloadChange(fileDownload: FileDownload) {}

    /**
     * Update the state of download an remove of jobs
     * @param downloadResult DownloadResult
     */
    override fun onDownloadFinish(downloadResult: DownloadResult) {
        val newState = when (downloadResult) {
            is DownloadResult.Success -> DownloadState.SUCCESS
            is DownloadResult.Failed -> DownloadState.FAIL
        }
        repository.updateStateDownload(downloadResult.fileDownload.uuid, newState)
        jobs.removeAll { it.fileDownload.uuid == downloadResult.fileDownload.uuid }
    }

    /**
     * Update the state to new state downloading
     * @param fileDownload FileDownload
     */
    override fun onDownloadStart(fileDownload: FileDownload) {
        repository.updateStateDownload(fileDownload.uuid, DownloadState.DOWNLOADING)
    }

}