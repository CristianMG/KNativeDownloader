package com.cristianmg.knativedownloader.data.database

import com.cristianmg.knativedownloader.db.knativedownloaderdatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

/**
 * JVM driver implementation
 * @return SqlDriver
 */
actual fun getSQlDriver(): SqlDriver {
    val driver =  JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    knativedownloaderdatabase.Schema.create(driver)
    return driver
}

