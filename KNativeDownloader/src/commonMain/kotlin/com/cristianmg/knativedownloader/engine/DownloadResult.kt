package com.cristianmg.knativedownloader.engine

import com.cristianmg.knativedownloader.model.FileDownload

/**
 * This class represents the download's state for each moment
 * @property fileDownload FileDownload file where was save the file
 */
sealed class DownloadResult(open var fileDownload: FileDownload){


    /**
     * Download was failed
     * @property exception Exception exception report for the engine
     * @property message String? message received from the engine can be null
     * @constructor
     */
    data class Failed(override var fileDownload: FileDownload, val exception: Exception, val message:String?):DownloadResult(fileDownload)

    /**
     * Download was success
     * @constructor
     */
    data class Success(override var fileDownload: FileDownload):DownloadResult(fileDownload)

}