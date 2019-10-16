package com.cristianmg.knativedownloader.model

/**
 * @property bytesDownloaded Long how many bytes there are downloaded at the moment
 * @constructor
 */
data class FileProgress(
        var bytesDownloaded: Long = 0L
)