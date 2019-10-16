package com.cristianmg.knativedownloader.engine

import com.cristianmg.knativedownloader.model.FileDownload

/**
 * Callback engine that use to report to @KNativeDownloader about download process
 */
interface DownloaderEngineListener{

    /**
     * This function is call to report that download progress change
     * @param fileDownload FileDownload file download with the progress of file
     */
    fun onProgressDownloadChange(fileDownload: FileDownload)

    /**
     * This function is call when the download finish with any result
     * @param downloadResult DownloadResult
     */
    fun onDownloadFinish(downloadResult: DownloadResult)

}