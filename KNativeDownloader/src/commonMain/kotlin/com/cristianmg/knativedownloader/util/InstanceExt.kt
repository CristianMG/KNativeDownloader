package com.cristianmg.knativedownloader.util

import com.cristianmg.knativedownloader.log.Logger


/**
 * @param function Predicate lambda to delegate to method who call
 * the execution
 * @return Unit
 *
 * The method only execute if BuildConfig is debug
 * @see Timber
 * **/
fun runDebug(function: () -> Unit) {
        function()
}

/**
 * @param function Predicate lambda to delegate to method who call
 * the element
 * @return Unit
 *
 * The method only execute if BuildConfig is debug
 * @see Timber
 * **/
fun <T> List<T>.logD(function: (element: T) -> String) {
    runDebug {
        if (isNotEmpty()) {
            forEach { element ->
                 Logger.d(function(element))
            }
        } else {
            Logger.d("The list is empty")
        }

    }
}


