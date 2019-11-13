package com.cristianmg.knativedownloader.data.database

import com.cristianmg.knativedownloader.db.knativedownloaderdatabase
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
    val instance: knativedownloaderdatabase by lazy { knativedownloaderdatabase(getSQlDriver()) }
}