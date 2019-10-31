package com.cristianmg.knativedownloader.data.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

/**
 * JVM driver implementation
 * @return SqlDriver
 */
actual fun getSQlDriver(): SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)