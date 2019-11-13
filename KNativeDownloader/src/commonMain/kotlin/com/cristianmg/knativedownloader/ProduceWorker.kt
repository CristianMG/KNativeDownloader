package com.cristianmg.knativedownloader

expect class ProduceWorker(
        kNativeDownloader: KNativeDownloader,
        queue: DownloadQueue) {

    /**
     * Indicate if queue is busy
     */
    val isQueueBusy:Boolean

    fun initProducer()
    fun closeProducer()

}



