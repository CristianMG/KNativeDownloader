package com.cristianmg.knativedownloader.data.database

import com.cristianmg.knativedownloader.database.KNativeDownloaderDatabase
import com.cristianmg.knativedownloader.log.Logger

/**
 * Singleton database
 */
object Database {

    init {
        Logger.d("Init database")
    }

    /**
     * Singleton instance
     */
    val instance: KNativeDownloaderDatabase by lazy { KNativeDownloaderDatabase(getSQlDriver()) }
}