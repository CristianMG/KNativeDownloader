package com.cristianmg.knativedownloader.data.database

import com.cristianmg.knativedownloader.db.knativedownloaderdatabase
import com.cristianmg.knativedownloader.log.Logger
import com.squareup.sqldelight.db.SqlDriver

/**
 * Singleton database
 */
object Database {

    init {
        Logger.d("Init database")
    }

    val instance: knativedownloaderdatabase by lazy { knativedownloaderdatabase(driver) }
    val driver: SqlDriver by lazy { getSQlDriver() }


    fun clear() {
        instance.downloadQueries.removeAll()
    }

}