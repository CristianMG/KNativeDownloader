package com.cristianmg.knativedownloader

import com.cristianmg.knativedownloader.log.Logger
import java.util.concurrent.Executors

/**
 *
 * @property kNativeDownloader KNativeDownloader
 * @property fj ForkJoinPool
 * @property isQueueBusy Boolean
 * @constructor
 */
actual class ProduceWorker actual constructor(val kNativeDownloader: KNativeDownloader,
                                              val queue: DownloadQueue) {

    /**
     * Indicate if queue is busy
     */
    actual val isQueueBusy: Boolean
        get() = queue.isBusy()

    private var executor = Executors.newSingleThreadExecutor()
    private var isCanceledThread: Boolean = false


    /**
     * Init producer
     */
    actual fun initProducer() {
        isCanceledThread = false
        executor.submit {
            try {
                while (!isCanceledThread) {
                    run(this)
                    Thread.sleep(1000)
                }
            } catch (exception: InterruptedException) {
                Logger.e("Error executing the producer", exception)
            }
        }
    }

    /**
     * Close producer
     */
    actual fun closeProducer() {
        isCanceledThread = true
    }

    /**
     * Add items to queue
     */
    private fun queueHasPrepared() {
        queue.addNextItemToQueue()
    }

    companion object {
        /**
         * Check if queue is prepared to add new threads
         */
        fun run(produceWorker: ProduceWorker) {
            synchronized(produceWorker) {
                if (produceWorker.isQueueBusy) {
                    return
                }
                produceWorker.queueHasPrepared()
            }
        }
    }
}