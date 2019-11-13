package com.cristianmg.knativedownloader.model

import com.cristianmg.knativedownloader.engine.DownloadResult
import kotlinx.coroutines.Deferred

/**
 * This class relate the job with their file download
 * @property fileDownload FileDownload
 * @property job Deferred<DownloadResult>
 * @constructor
 */
data class DownloadJob(
        val fileDownload: FileDownload,
        val job:Deferred<DownloadResult>)