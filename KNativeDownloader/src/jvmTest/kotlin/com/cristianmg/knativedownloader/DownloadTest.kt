package com.cristianmg.knativedownloader

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.IllegalStateException

class DownloadTest {

    @Test
    fun checkDownload() {
        runBlocking {
            KNativeDownloader()
                    .addItemToQueue("https://file-examples.com/wp-content/uploads/2017/11/file_example_MP3_700KB.mp3")
        }
    }

    private fun getMockClient(): HttpClient {
        return HttpClient(MockEngine.create {
            addHandler { request ->
                when (request.url.fullPath) {
                    "http://www.example.com/item.zip" -> {
                        val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Text.Plain.toString()))
                        val inputStream = this.javaClass.getResourceAsStream("assets/example_files.zip")
                        return@addHandler respond(inputStream.readBytes(), headers = responseHeaders)
                    }
                    else -> throw IllegalStateException("Error this call is wrong")
                }
            }
        })
    }
}
