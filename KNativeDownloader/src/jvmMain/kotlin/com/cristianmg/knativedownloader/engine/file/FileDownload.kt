package com.cristianmg.knativedownloader.engine.file

import org.apache.commons.io.FilenameUtils
import java.net.URI
import java.util.*

actual fun getExtensionFromUrl(url: String): String {
    val uri = URI(url)
    return FilenameUtils.getExtension(uri.path)
}

actual fun getUUID(): String = UUID.randomUUID().toString()