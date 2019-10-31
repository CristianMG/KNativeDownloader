package com.cristianmg.knativedownloader

import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.coroutines.io.jvm.javaio.toByteReadChannel
import kotlinx.io.core.ExperimentalIoApi
import java.io.File

@ExperimentalIoApi
actual fun getByteReadChannelFromFile(path: String): ByteReadChannel = File(path).inputStream().toByteReadChannel()
actual fun getContentLenghtFromFile(path: String): Long {
  return File(path).length()
}
