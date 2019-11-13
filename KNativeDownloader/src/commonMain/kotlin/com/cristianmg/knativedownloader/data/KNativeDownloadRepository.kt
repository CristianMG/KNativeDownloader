package com.cristianmg.knativedownloader.data

import com.cristianmg.knativedownloader.data.database.Database
import com.cristianmg.knativedownloader.model.DownloadState
import com.cristianmg.knativedownloader.model.FileDownload

/**
 * Repository to wrapper all petition
 * @property database KNativeDownloaderDatabase instance sqldelight database implementation
 * @constructor
 */
class KNativeDownloadRepository(
        private val database: Database
) {
    companion object {
        /**
         * Singleton instance
         */
        val instance: KNativeDownloadRepository by lazy { KNativeDownloadRepository(Database) }
    }

    /***
     * Insert in database a download waiting for engine start to download
     * @param file FileDownload
     */
    fun insertDownload(file: FileDownload) {
        database.instance.downloadQueries.insert(file.url, file.uuid, file.sizeFile, file.extension, file.downloadedPath,file.state.state)
    }

    /***
     * Delete download from queue
     * @param url String
     */
    fun deleteDownload(url: String) {
        database.instance.downloadQueries.remove(url)
    }

    fun getDownloadAtLimit(limit: Long): List<FileDownload> {
        return database.instance.downloadQueries.selectAll(limit)
                .executeAsList()
                .map {
                    FileDownload(it.url, it.size_file, it.uuid, it.extension, it.downloaded_path)
                }
    }

    fun getNextQueueItem(): FileDownload? {
        return database.instance.downloadQueries.nextDownload(1)
                .executeAsList()
                .map {
                    FileDownload(it.url, it.size_file, it.uuid, it.extension, it.downloaded_path)
                }
                .firstOrNull()
    }

    fun updateStateDownload(uuid: String, newState: DownloadState) {

    }

}
