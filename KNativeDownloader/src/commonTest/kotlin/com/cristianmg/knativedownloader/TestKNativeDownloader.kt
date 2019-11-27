package com.cristianmg.knativedownloader

import kotlin.test.Test

class TestKNativeDownloader {

    private val downloader: KNativeDownloader by lazy { KNativeDownloader() }

    @Test
    fun `add download in queue`(): Unit = runTest {
        downloader.init()
        downloader.downloadFile(getFileDownload())
        downloader.stop()
    }

    companion object {

        fun getFileDownload(): String {
            return "http://www.example.com/example_document_1.pdf"
        }
    }
}
