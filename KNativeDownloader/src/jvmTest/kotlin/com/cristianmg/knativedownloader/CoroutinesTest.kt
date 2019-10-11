package com.cristianmg.knativedownloader

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

@UseExperimental(ExperimentalCoroutinesApi::class)
actual fun runTest(block: suspend () -> Unit)  = runBlockingTest { block() }