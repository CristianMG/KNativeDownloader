package com.cristianmg.knativedownloader

import com.cristianmg.knativedownloader.data.FakeDownloads
import com.cristianmg.knativedownloader.data.KNativeDownloadRepository
import com.cristianmg.knativedownloader.data.database.Database
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertTrue

class TestDownloadQueue {

    private val queue: DownloadQueue by lazy { DownloadQueue(MockClient.getMockClient()) }
    private val repository: KNativeDownloadRepository by lazy { KNativeDownloadRepository.instance }

    @AfterTest
    fun afterTest(){
        Database.clear()
    }

    @Test
    fun `add item to queue`() {
        val fakeDownload = FakeDownloads.getFileDownload()
        repository.insert(fakeDownload)
        queue.addNextItemToQueue()
        assertTrue { queue.size == 1 }
    }

    @Test
    fun `add item and cancel`() {
        val fakeDownload = FakeDownloads.getFileDownload()
        repository.insert(fakeDownload)
        queue.addNextItemToQueue()
        queue.cancel(fakeDownload)
        assertTrue { queue.size == 0 }
    }
}