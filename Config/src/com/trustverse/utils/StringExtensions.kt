package com.trustverse.utils

import kotlin.reflect.KClass

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

fun String.toType(type: String): Any {
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

fun String.toType(type: Class<*>): Any {
    when (type) {
        String::class.java -> return this
        Boolean::class.java -> return toBoolean()
        Byte::class.java -> return toByte()
        Int::class.java -> return toInt()
        Long::class.java -> return toLong()
        Float::class.java -> return toFloat()
        Double::class.java -> return toDouble()
        else -> throw IllegalArgumentException("Invalid \"type\" argument value.")
    }
}


fun String.toType(type: KClass<*>): Any {
    when (type) {
        String::class -> return this
        Boolean::class -> return toBoolean()
        Byte::class -> return toByte()
        Int::class -> return toInt()
        Long::class -> return toLong()
        Float::class -> return toFloat()
        Double::class -> return toDouble()
        else -> throw IllegalArgumentException("Invalid \"type\" argument value.")
    }
}