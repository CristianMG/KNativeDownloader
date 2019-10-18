package com.cristianmg.knativedownloader

import com.cristianmg.knativedownloader.client.HttpClient
import com.cristianmg.knativedownloader.data.KNativeDownloadRepository
import com.cristianmg.knativedownloader.engine.DownloadResult
import com.cristianmg.knativedownloader.engine.DownloaderEngine
import com.cristianmg.knativedownloader.engine.DownloaderEngineListener
import com.cristianmg.knativedownloader.log.Logger
import com.cristianmg.knativedownloader.model.DownloadJob
import com.cristianmg.knativedownloader.model.FileDownload
import kotlinx.coroutines.*
import kotlin.jvm.Synchronized

/**
 * @property httpClient HttpClient ktor client to do downloads
 * @property engine DownloaderEngine instance of engine
 * @constructor
 */
class KNativeDownloader(
        private val httpClient: io.ktor.client.HttpClient = HttpClient.clientDefault(),
        private val parallelDownloads: Long = PARALLEL_DOWNLOADS_DEFAULT
) : DownloaderEngineListener {

    private val engine: DownloaderEngine by lazy { DownloaderEngine(httpClient, this) }
    private val repository: KNativeDownloadRepository by lazy { KNativeDownloadRepository.instance }

    private var jobs: MutableList<DownloadJob> = mutableListOf()
    //private val worker:Worker

    /**
     * Add item to database for the next iteration download
     * @param url String
     */
    fun addItemToQueue(url: String) {
        val download = FileDownload(url)
        repository.insertDownload(download)
        checkDownload()
    }

    /**
     * Stop download if it's started and remove from queue
     * @param url String url which we want remove and stop from downloader
     */
    fun stopAndRemoveDownload(url: String) {
        val downloadJob = getDownloadJobFromUrl(url)
        if (downloadJob != null) {
            if (downloadJob.job.isActive)
                downloadJob.job.cancel("Download cance by client")
            jobs.remove(downloadJob)
        }
        repository.deleteDownload(url)
    }

    /**
     * Stop all downloads and clear queue
     */
    fun stopAllDownloads() {
        jobs.forEach { it.job.cancel("Cancel by client") }
        jobs.clear()
    }


    fun getDownloadJobFromUrl(url: String): DownloadJob? {
        return jobs.firstOrNull { it.fileDownload.url == url }
    }

    /**
     * Check and add jobs to deffered
     */
    fun checkDownload() {
        if (jobs.size < PARALLEL_DOWNLOADS_DEFAULT) {
            repository.getDownloadAtLimit(parallelDownloads)
                    .apply {
                        forEach { fileDownload -> jobs.add(DownloadJob(fileDownload, GlobalScope.async { engine.downloadFile(fileDownload) })) }
                    }
        } else {
            Logger.d("Maximum parallel downloads downloaded")
        }
    }

    @Synchronized
    override fun onProgressDownloadChange(fileDownload: FileDownload) {
    }

    @Synchronized
    override fun onDownloadFinish(downloadResult: DownloadResult) {
        stopAndRemoveDownload(downloadResult.fileDownload.url)
    }


    companion object {
        /**
         * Number of downloads that will do at the same time
         */
        const val PARALLEL_DOWNLOADS_DEFAULT: Long = 1
    }
}