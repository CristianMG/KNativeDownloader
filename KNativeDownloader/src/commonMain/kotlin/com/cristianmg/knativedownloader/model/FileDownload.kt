package com.cristianmg.knativedownloader.model

/**
 * This class contains all information about the download
 * @property url String remote url which found the resource
 * @property sizeFile Long size file
 * @property uuid String uuid unique to reference download
 * @property extension String extensio of download
 * @property downloadedPath File path where file was downloaded can be null if download was successful or not
 * @property state represents the currently state of download
 * @constructor
 */
data class FileDownload(
        val url: String,
        var sizeFile: Long? = null,
        val uuid: String = getUUID(),
        val extension: String = getExtensionFromUrl(url),
        var downloadedPath: String? = null,
        var state:DownloadState = DownloadState.PENDING
)
/**
 * @param url String url to extract extension if exist
 * @return String extension of url
 */
expect fun getExtensionFromUrl(url: String): String

/**
 * @return String a random uuid
 */
expect fun getUUID(): String