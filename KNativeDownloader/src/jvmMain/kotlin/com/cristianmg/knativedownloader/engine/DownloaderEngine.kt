package com.cristianmg.knativedownloader.engine

import com.cristianmg.knativedownloader.engine.file.FileDownload
import com.cristianmg.knativedownloader.log.Logger
import com.sun.jndi.toolkit.url.Uri
import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.http.*
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.cio.writeChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.coroutines.io.copyAndClose
import kotlinx.coroutines.io.copyTo
import kotlinx.coroutines.io.jvm.javaio.toInputStream
import kotlinx.coroutines.io.jvm.nio.copyTo
import kotlinx.coroutines.io.skipDelimiter
import kotlinx.io.core.IoBuffer
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.nio.ByteBuffer
import java.nio.channels.Pipe
import java.util.*


/**
 * JVM implementation save the buffer stream
 */
@KtorExperimentalAPI
actual suspend fun saveBufferStreamToFile(channel: ByteReadChannel, fileDownload: FileDownload, bytesRead: (fileDownload: FileDownload) -> Unit) {
    val file = File.createTempFile(fileDownload.uuid, ".${fileDownload.extension}")
    val writeChannel = file.writeChannel()

    val bufferLimit = 1024L
    do {
        fileDownload.bytesDownloaded += channel.copyTo(writeChannel, bufferLimit)
        bytesRead(fileDownload)
    } while (channel.availableForRead > 0)

    writeChannel.flush()

}