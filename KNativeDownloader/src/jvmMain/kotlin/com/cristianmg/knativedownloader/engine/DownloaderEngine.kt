package com.cristianmg.knativedownloader.engine

import com.cristianmg.knativedownloader.engine.file.FileDownload
import com.cristianmg.knativedownloader.log.Logger
import com.sun.jndi.toolkit.url.Uri
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.http.*
import io.ktor.util.cio.writeChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.coroutines.io.copyAndClose
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.util.*


/**
 * JVM implementation save the buffer stream
 */
actual suspend fun saveBufferStremToFile(channel: ByteReadChannel, fileDownload: FileDownload) {
    val file = File.createTempFile(fileDownload.getRandomUUID(), ".${fileDownload.getExtension()}")
    channel.copyAndClose(file.writeChannel())
}