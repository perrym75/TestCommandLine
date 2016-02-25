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

fun String.convertValue(type: String): Any {
    when (type) {
        "String" -> return this
        "Boolean" -> return toBoolean()
        "Byte" -> return toByte()
        "Integer" -> return toInt()
        "Long" -> return toLong()
        "Float" -> return toFloat()
        "Double" -> return toDouble()
        else -> throw IllegalArgumentException("Invalid \"type\" argument value.")
    }
}