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
                ParamInfo("-debug", true, Debug.javaClass, { x -> Debug = x as Boolean }),
                ParamInfo("-trace", false/*, Trace.javaClass, { x -> Trace = x as Boolean }*/),
                ParamInfo("-log", true, Log.javaClass, { x -> Log = x as String }),
                ParamInfo("-count", true, Count.javaClass, { x -> Count = x as Int })),
                { x -> x.startsWith("-") })

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