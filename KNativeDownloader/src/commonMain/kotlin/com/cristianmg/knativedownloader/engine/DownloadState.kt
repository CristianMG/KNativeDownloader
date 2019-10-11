package com.cristianmg.knativedownloader.engine

/**
 * This class represents the download's state for each moment
 */
data class DownloadState(
        val percentProgress: Int,
        val speedDownload: Int
)