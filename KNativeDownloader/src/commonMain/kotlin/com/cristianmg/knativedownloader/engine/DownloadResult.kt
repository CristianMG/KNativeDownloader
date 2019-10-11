package com.cristianmg.knativedownloader.engine

import com.cristianmg.knativedownloader.engine.file.FileDownload

/**
 * This class represents the download's state for each moment
 */
sealed class DownloadResult{


    /**
     * @property exception Exception exception report for the engine
     * @property message String? message received from the engine can be null
     * @constructor
     */
    data class Failed(val exception: Exception, val message:String?):DownloadResult()

    /**
     * @property fileDownload FileDownload file where was save the file
     * @constructor
     */
    data class Success(val fileDownload: FileDownload):DownloadResult()

}