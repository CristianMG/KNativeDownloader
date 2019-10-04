package com.cristianmg.knativeplayer.log

import com.github.aakira.napier.DebugAntilog
import com.github.aakira.napier.Napier


/**
 * Singleton for android logger
 */
object AndroidLogger {
    init {
        Napier.base(DebugAntilog())
        Napier.d("Initializing logger")
    }
    /**
     * Returns the singleton instance for IOS logger
     */
    fun logger(): Napier = Napier
}


/**
 * Actual function to return the android logger
 */
actual fun getLog(): Napier {
    return AndroidLogger.logger()
}