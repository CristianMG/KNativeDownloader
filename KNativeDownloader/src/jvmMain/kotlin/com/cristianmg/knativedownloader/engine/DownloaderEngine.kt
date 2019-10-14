package com.cristianmg.knativedownloader.engine

import com.cristianmg.knativedownloader.engine.file.FileDownload
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.cio.writeChannel
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.coroutines.io.copyTo
import java.io.File


/**
 * JVM implementation save the buffer stream
 */
@KtorExperimentalAPI
actual suspend fun saveBufferStreamToFile(channel: ByteReadChannel, fileDownload: FileDownload, bytesRead: (byteDownloaded: Long) -> Unit): String {
    val file = File.createTempFile(fileDownload.uuid, ".${fileDownload.extension}")
    val writeChannel = file.writeChannel()

    val bufferLimit = 1024L
    var downloadedBytes = 0L
    do {
        downloadedBytes += channel.copyTo(writeChannel, bufferLimit)
        bytesRead(downloadedBytes)
    } while (channel.availableForRead > 0)

    writeChannel.flush()
    return file.absolutePath
}