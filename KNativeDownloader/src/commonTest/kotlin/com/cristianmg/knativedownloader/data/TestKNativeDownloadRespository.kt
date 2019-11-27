package com.cristianmg.knativedownloader.data

import com.cristianmg.knativedownloader.data.database.Database
import com.cristianmg.knativedownloader.log.Logger
import com.cristianmg.knativedownloader.model.DownloadState
import com.cristianmg.knativedownloader.runTest
import com.cristianmg.knativedownloader.util.logD
import kotlin.test.*

class TestKNativeDownloadRespository {

    private val repository: KNativeDownloadRepository by lazy { KNativeDownloadRepository.instance }

    @AfterTest
    fun afterTest(){
        Database.clear()
    }

    @Test
    fun `insert download in repository`(): Unit = runTest {
        val fakeDownload = FakeDownloads.getFileDownload()
        repository.insert(fakeDownload)
        val downloadFromRepository =  repository.getNextQueueItem()
        assertTrue("The download $fakeDownload must be the same  $downloadFromRepository") { downloadFromRepository == fakeDownload }
    }


    @Test
    fun `is order in get next queue item properly`(): Unit = runTest {
        val downloads = FakeDownloads.getListDownload(10)
        downloads.forEach { repository.insert(it) }

        downloads.logD { it.uuid }

        downloads.forEach { fileDownload ->
            repository.getNextQueueItem()?.let {
                assertTrue("The download $it is not the same $fileDownload") { it == fileDownload }
                Logger.d("The download $it same as $fileDownload")
                repository.updateStateDownload(fileDownload.uuid, DownloadState.SUCCESS)
            } ?: kotlin.run {
                assertFails { IllegalStateException("The next download cannot be null") }
            }
        }
    }


    @Test
    fun `update download state`(): Unit = runTest {
        val fakeDownload = FakeDownloads.getFileDownload()
        repository.insert(fakeDownload)
        fakeDownload.state = DownloadState.SUCCESS

        repository.updateStateDownload(fakeDownload.uuid, DownloadState.SUCCESS)
        repository.get(fakeDownload.url).apply {
            assertTrue("The state $state must be success ") { state == DownloadState.SUCCESS }
        }
    }


    @Test
    fun `delete item`(): Unit = runTest {
        val fakeDownload = FakeDownloads.getFileDownload()
        repository.insert(fakeDownload)
        repository.delete(fakeDownload.url)
        assertFailsWith<NoSuchElementException> { repository.get(fakeDownload.url) }
    }


}