package com.cristianmg.knativedownloader.data

import com.cristianmg.knativedownloader.KNativeDownloaderDatabase
import com.cristianmg.knativedownloader.data.database.Database
import com.cristianmg.knativedownloader.model.FileDownload

/**
 * Repository to wrapper all petition
 * @property database KNativeDownloaderDatabase instance sqldelight database implementation
 * @constructor
 */
class KNativeDownloadRepository(
        private val database: KNativeDownloaderDatabase
) {
    companion object {
        /**
         * Singleton instance
         */
        val instance: KNativeDownloadRepository by lazy { KNativeDownloadRepository(Database.instance) }
    }

    /***
     * Insert in database a download waiting for engine start to download
     * @param file FileDownload
     */
    fun insertDownload(file: FileDownload) {
        database.downloadQueries.insert(file.url, file.uuid, file.sizeFile, file.extension, file.downloadedPath)
    }

    /***
     * Delete download from queue
     * @param url String
     */
    fun deleteDownload(url: String) {
        database.downloadQueries.remove(url)
    }

    fun getDownloadAtLimit(limit: Long): List<FileDownload> {
        return database.downloadQueries.selectAll(limit)
                .executeAsList()
                .map {
                    FileDownload(it.url, it.size_file, it.uuid, it.extension, it.downloaded_path)
                }
    }

}
