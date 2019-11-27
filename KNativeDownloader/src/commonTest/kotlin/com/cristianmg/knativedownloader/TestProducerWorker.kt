package com.cristianmg.knativedownloader

import kotlin.test.Test

class TestProducerWorker{

    private val queue: DownloadQueue by lazy { DownloadQueue() }
    private val produceWorker: ProduceWorker by lazy { ProduceWorker(queue) }

    @Test
    fun `producer worker add items to queue`(){
        produceWorker.initProducer()

        produceWorker.closeProducer()
    }
}