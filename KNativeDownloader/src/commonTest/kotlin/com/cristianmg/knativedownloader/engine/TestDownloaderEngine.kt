package com.cristianmg.knativedownloader.engine

import com.cristianmg.knativedownloader.MockClient
import com.cristianmg.knativedownloader.model.FileDownload
import com.cristianmg.knativedownloader.runTest
import com.cristianmg.knativedownloader.util.existFile
import kotlinx.coroutines.*
import kotlin.test.*

class TestDownloaderEngine {

    private val engine: DownloaderEngine by lazy { DownloaderEngine(MockClient.getMockClient(), null) }

    @Test
    fun `engine is able to download a file example`(): Unit = runTest {
        val result = engine.downloadFile(getFileDownloadTestDocument())
        checkDownloadResult(result)
    }


    @Test
    fun `download multiple files`(): Unit = runTest {
        val one = CoroutineScope(Dispatchers.Unconfined).async { engine.downloadFile(getFileDownloadTestDocument(1)) }
        val two = CoroutineScope(Dispatchers.Unconfined).async { engine.downloadFile(getFileDownloadTestDocument(2)) }
        val three = CoroutineScope(Dispatchers.Unconfined).async { engine.downloadFile(getFileDownloadTestDocument(3)) }
        val list:List<DownloadResult> = listOf(one,two,three).awaitAll()
        for (result in list) {
            checkDownloadResult(result)
        }
    }

    @Test
    fun `engine return a 404 error when get a paged not exist`(): Unit = runTest {
        val downloadResult = engine.downloadFile(FileDownload(URL_WASNT_FOUND))
        assertTrue {
            downloadResult is DownloadResult.Failed
        }
    }

    private fun checkDownloadResult(downloadResult:DownloadResult){
        if (downloadResult is DownloadResult.Success) {
            val fileDownload = downloadResult.fileDownload
            assertNotNull("The path must not be null") { fileDownload.downloadedPath }
            assertTrue { existFile(fileDownload.downloadedPath!!) }
        } else {
            assertFails { IllegalStateException("The result must be a success") }
        }
    }


    companion object {
        const val URL_WASNT_FOUND: String = "http://www.example.com/not_found.zip"
        fun getFileDownloadTestDocument(document: Int = 1): FileDownload {
            return FileDownload("http://www.example.com/example_document_$document.pdf")
        }
    }
}
