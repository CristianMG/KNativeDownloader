package com.cristianmg.knativedownloader

import kotlinx.coroutines.*

/*
@UseExperimental(ExperimentalCoroutinesApi::class)
actual fun runTest(block: suspend () -> Unit)  = runBlockingTest() { block() }
*/


/**
 * Workaround run blocking test does not work properly see @https://github.com/Kotlin/kotlinx.coroutines/issues/1222
 * using run blocking instead until bug will be fixed
 * */
@UseExperimental(ExperimentalCoroutinesApi::class)
actual fun runTest(block: suspend () -> Unit): Unit = runBlocking { block() }

