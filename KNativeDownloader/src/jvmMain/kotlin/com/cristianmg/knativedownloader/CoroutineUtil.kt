package com.cristianmg.knativedownloader

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

actual fun getCurrentThread() : String = Thread.currentThread().toString()


/**
 * Create a new scope by platform
 * @return CoroutineScope
 */
actual fun getCoroutineScope(): CoroutineScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob())
