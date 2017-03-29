/**
 * Created 24.02.2016.
 * @author Goussein Minkailov
 */

package com.trustverse.config

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties

class ParamsConfig(args: Array<String>) {
    var Debug: Boolean = false
    var Log: String = ""
    var Trace: Boolean = false
    var Count: Int = 0
    val UnnamedParams: List<String>

    init {
        val parser = CommandLineParser(args, listOf(
                ParamInfo("-debug", true, Debug.javaClass, { x -> Debug = x as Boolean }),
                ParamInfo("-trace", false, Trace.javaClass, { Trace = true }),
                ParamInfo("-log", true, Log.javaClass, { x -> Log = x as String }),
                ParamInfo("-count", true, Count.javaClass, { x -> Count = x as Int })),
                { x -> x.startsWith("-") })

        UnnamedParams = parser.UnnamedParams
    }

    fun print() {
        val cl = ParamsConfig::class
        cl.declaredMemberProperties.filter {it.visibility != KVisibility.PRIVATE}.forEach {
            println("${it.name} = ${it.get(this)}")
        }
    }
}