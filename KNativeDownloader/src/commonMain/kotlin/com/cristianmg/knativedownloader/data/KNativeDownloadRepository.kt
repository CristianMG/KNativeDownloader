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
     * Crud Insert in database download
     * @param file FileDownload
     */
    fun insert(file: FileDownload) {
        database.instance.downloadQueries.insert(file.url, file.uuid, file.sizeFile, file.extension, file.downloadedPath, file.state.state)
    }

    /***
     * Crud delete download from queue searching by id
     * @param url String
     */
    fun delete(url: String) {
        database.instance.downloadQueries.remove(url)
    }


    /**
     * Crud return an item searching by id
     * @param url String
     * @return FileDownload
     */
    fun get(url:String):FileDownload{
        return database.instance.downloadQueries.getById(url)
                .executeAsList()
                .map {
                    FileDownload(it.url, it.size_file, it.uuid, it.extension, it.downloaded_path, DownloadState.valueOf(it.state))
                }.first()
    }

    /**
     * Return the next element should be downloaded
     * @return FileDownload?
     */
    fun getNextQueueItem(): FileDownload? {
        return database.instance.downloadQueries.nextDownload(1)
                .executeAsList()
                .map {
                    FileDownload(it.url, it.size_file, it.uuid, it.extension, it.downloaded_path, DownloadState.valueOf(it.state))
                }
                .firstOrNull()
    }

    /**
     * Update the sate of download
     * @param uuid String
     * @param newState DownloadState
     */
    fun updateStateDownload(uuid: String, newState: DownloadState) {
        database.instance.downloadQueries.updateStateDownload(newState.state,uuid)
    }

}
