package com.cristianmg.knativedownloader.data

import com.github.aakira.napier.Napier

/**
 * Returns the log
 */
object Logger {
    /**
     * Log into console a debug message
     */
    fun d(message: () -> String, throwable: Throwable? = null, tag: String? = null) = getLog().d(message, throwable, tag)

    /**
     * Log into console a debug message
     */
    fun d(message: String, throwable: Throwable? = null, tag: String? = null) = getLog().d(message, throwable, tag)

    /**
     * Log into console a error message
     */
    fun e(message: String, throwable: Throwable? = null, tag: String? = null) = getLog().e(message, throwable, tag)

    /**
     * Log into console a error message
     */
    fun e(message: () -> String, throwable: Throwable? = null, tag: String? = null) = getLog().e(message, throwable, tag)
}

/**
 * This function returns diferents log for devices
 */
expect fun getLog(): Napier