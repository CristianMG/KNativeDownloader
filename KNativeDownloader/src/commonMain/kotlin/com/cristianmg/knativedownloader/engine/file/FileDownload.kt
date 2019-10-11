package com.cristianmg.knativedownloader.engine.file

/**
 * This class contains all information about the download
 * @property url String remote url which found the resource
 * @property sizeFile Long size file
 * @property uuid String uuid unique to reference download
 * @property extension String extensio of download
 * @property bytesDownloaded Long bytes read at the moment
 * @constructor
 */
data class FileDownload(
        val url: String,
        val sizeFile: Long,
        val uuid: String = getUUID(),
        val extension: String = getExtensionFromUrl(url),
        var bytesDownloaded:Long = 0L)

/**
 * @param url String url to extract extension if exist
 * @return String extension of url
 */
expect fun getExtensionFromUrl(url: String): String

/**
 * @return String a random uuid
 */
expect fun getUUID(): String