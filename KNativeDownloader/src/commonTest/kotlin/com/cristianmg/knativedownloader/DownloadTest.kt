package com.cristianmg.knativedownloader


import kotlin.test.Test

class DownloadTest {

    @Test
    fun checkDownload() = runTest {
        KNativeDownloader(MockClient.getMockClient())
                .addItemToQueue("http://www.example.com/zip_file.zip")
    }


}
