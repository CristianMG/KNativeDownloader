package com.cristianmg.knativedownloader.model

/**
 * Represents the state of download
 * @propert state is the key to save in database
 * @constructor
 */
enum class DownloadState(val state:String) {

    /**
     * The download is waiting to enter the queue
     */
    PENDING("PENDING"),

    /**
     * The download is in queue and it started to download
     */
    DOWNLOADING("DOWNLOADING"),

    /**
     * The download is downloaded the process was finish with state success
     */
    SUCESS("SUCCESS"),

    /**
     * The download is downloaded the process was finish with state fail
     */
    FAIL("FAIL")
}