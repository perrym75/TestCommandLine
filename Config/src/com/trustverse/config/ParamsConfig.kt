package com.trustverse.config

import java.lang.reflect.Modifier
import kotlin.reflect.KCallable
import kotlin.reflect.declaredMemberProperties
import kotlin.reflect.jvm.javaField

/**
 * Created by g.minkailov on 24.02.2016.
 */
class ParamsConfig(private val args: Array<String>) {
    var Debug: Boolean = false
    var Log: String = ""
    var Trace: Boolean = false
    var Count: Int = 0
    val UnnamedParams: List<String>

    init {
        val parser = CommandLineParser(args, listOf(
                ParamInfo("-debug", true, Debug.javaClass),
                ParamInfo("-trace", false),
                ParamInfo("-log", true, Log.javaClass),
                ParamInfo("-count", true, Count.javaClass)),
                { x -> x.startsWith("-") })
        Debug = parser["-debug"]?.Value as Boolean? ?: false
        Trace = parser["-trace"]?.Presents ?: false
        Log = parser["-log"]?.Value as String? ?: ""
        Count = parser["-count"]?.Value as Int? ?: 0

        UnnamedParams = parser.UnnamedParams
    }


    fun print() {
        val cl = ParamsConfig::class
        for (field in cl.declaredMemberProperties.filter({x -> x.javaField?.modifiers == Modifier.PRIVATE})) {
            println("${field.name} = ${field.get(this)}")
        }

        for (value in UnnamedParams) {
            println("Unnamed value = $value")
        }
    }
}