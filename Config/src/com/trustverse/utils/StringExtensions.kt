package com.trustverse.utils

/**
 * Created by g.minkailov on 25.02.2016.
 */

fun String.toBoolean(): Boolean {
    return when (this) {
        "1" -> true
        "0" -> false
        "true" -> true
        "false" -> false
        else -> throw NumberFormatException()
    }
}
