package com.cristianmg.knativedownloader

actual fun getCurrentThread() : String = Thread.currentThread().toString()
