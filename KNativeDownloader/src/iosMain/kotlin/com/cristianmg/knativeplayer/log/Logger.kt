package com.cristianmg.knativeplayer.log

import com.github.aakira.napier.DebugAntilog
import com.github.aakira.napier.Napier


/**
 * Singleton for android logger
 */
object IOSLogger {
    init {
        Napier.base(DebugAntilog())
        Napier.d("Initializing IOS logger")
    }
    /**
     * Returns the singleton instance for IOS logger
     */
    fun logger(): Napier = Napier
}

/**
 * Actual function to return the IOS logger
 */
actual fun getLog(): Napier {
    return IOSLogger.logger()
}