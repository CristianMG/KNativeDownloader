package com.cristianmg.knativedownloader

/**
 * Function producer is put in queue indefenitly
 * @property isQueueBusy Boolean indicate if the queue is busy
 * @constructor
 */
expect class ProduceWorker(
        queue: DownloadQueue) {

    /**
     * Indicate if queue is busy
     */
    val isQueueBusy:Boolean

    /**
     * Init producer which function is add to queue
     */
    fun initProducer()

    /**
     * Kill producer
     */
    fun closeProducer()

}



