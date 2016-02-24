package com.trustverse

import java.lang.reflect.Modifier
import java.util.regex.Pattern
import kotlin.reflect.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

/**
 * Created by g.minkailov on 24.02.2016.
 */
class ParamsConfig(private val args: Array<String>) {
    var Debug: Boolean = false
    var Log: Boolean = false

    fun processCommandLine() {
        var i = 0
        while (i < args.size) {
            try {
                var param = args[i]
                if (isParam(param)) {
                    var value = if (i + 1 < args.size)
                                    if (isParam(args[i + 1])) "" else args[i + 1]
                                else ""
                    when (param) {
                        "-debug" -> {
                            try {
                                Debug = value.toBoolean()
                                i++
                            } catch (e: Exception) {
                                throw IllegalArgumentException("Invalid \"${args[i]}\" param value \"${value}\". Value must be either true, false, 1 or 0.")
                            }
                        }
                        "-log" -> {
                            try {
                                Log = value.toBoolean()
                                i++
                            } catch (e: Exception) {
                                throw IllegalArgumentException("Invalid \"${args[i]}\" param value \"${value}\". Value must be either true, false, 1 or 0.")
                            }
                        }
                        else -> throw IllegalArgumentException("Invalid argument \"${args[i]}\".")
                    }
                }
            }
            catch (e: Exception) {
                throw e
            }
            i++
        }
    }

    fun isParam(str: String):Boolean {
        val p = Pattern.compile("-\\w+")
        val m = p.matcher(str)
        return m.matches()
    }

    fun print() {
        val cl = ParamsConfig::class
        for (field in cl.declaredMemberProperties.filter({x -> x.javaField?.modifiers == Modifier.PRIVATE})) {
            println("${field.name} = ${field.get(this)}")
        }
    }
}

fun String.toBoolean(): Boolean {
    return when (this) {
        "1" -> true
        "0" -> false
        "true" -> true
        "false" -> false
        else -> throw IllegalArgumentException()
    }
}
