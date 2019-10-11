package com.cristianmg.knativedownloader.engine.file

class FileDownload(val url: String) {

    fun getExtension() = getExtensionFromUrl(url)

    fun getRandomUUID() = getUUID()
}


/**
 * Returns an extension from url
 */
expect fun getExtensionFromUrl(url: String): String

/**
 * Returns a random uuid
 */
expect fun getUUID(): String