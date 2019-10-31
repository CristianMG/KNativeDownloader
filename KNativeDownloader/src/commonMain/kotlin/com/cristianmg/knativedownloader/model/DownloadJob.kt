package com.cristianmg.knativedownloader.model

import com.cristianmg.knativedownloader.engine.DownloadResult
import kotlinx.coroutines.Deferred

data class DownloadJob(
        val fileDownload: FileDownload,
        val job:Deferred<DownloadResult>)