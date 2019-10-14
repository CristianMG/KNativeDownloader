package com.cristianmg.knativedownloader

import com.cristianmg.knativedownloader.engine.DownloadResult
import com.cristianmg.knativedownloader.engine.DownloaderEngine
import com.cristianmg.knativedownloader.util.existFile
import kotlin.test.*

class TestDownloaderEngine {

    private val engine: DownloaderEngine by lazy { DownloaderEngine(MockClient.getMockClient(), null) }

    @Test
    fun `engine is able to download a file example`(): Unit = runTest {
        val downloadResult = engine.downloadFile(getUrlTestDocument())
        if (downloadResult is DownloadResult.Success) {
            val fileDownload = downloadResult.fileDownload
            assertNotNull("The path must not be null"){ fileDownload.downloadedPath }
            assertTrue { existFile(fileDownload.downloadedPath!!) }
        } else {
            assertFails { IllegalStateException("The result must be a success")  }
        }
    }


    @Test
    fun `engine return a 404 error when get a paged not exist`(): Unit = runTest {
        val downloadResult = engine.downloadFile(URL_WASNT_FOUND)
        assertTrue {
            downloadResult is DownloadResult.Failed
        }
    }


    companion object {
        const val URL_WASNT_FOUND: String = "http://www.example.com/not_found.zip"
        fun getUrlTestDocument(document: Int = 1): String {
            return "http://www.example.com/example_document_$document.pdf"
        }
    }
}
