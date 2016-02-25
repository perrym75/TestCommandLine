package com.trustverse.factoryconsumer

import com.classfactory.*
import com.trustverse.config.ParamsConfig
import java.util.regex.Pattern

fun main(args: Array<String>) {
    try {
        val params = ParamsConfig(args)
        params.print()

        val si1 = SomeFactory.newInstance(SomeClass2::class.java)
        si1.f()
        val s = test1()
        test(s ?: si1)//when (s) {null -> si1 else -> s})
        val si2: SomeInterface? = SomeFactory.newInstance(SomeClass1::class.java)
        val si3 = si2?.f()
        si3?.f()
        val a = intArrayOf(1, 2, 3, 4)
        println(a.filter({x -> x % 2 == 0}))

        val str = "(?<local>(\\w+(([-+._']\\w+)|(\\.\"([-+._']?\\w+)*\"\\.\\w+))*)|([\"]\\w+([-+._']\\w+)*[\"]))@(?<domain>(?<domain1>\\w+([-.]\\w+)*)[.](?<domain2>\\w+([-.]\\w+)*))"
        val p = Pattern.compile(str)
        val m = p.matcher("\"adf.g.kjh.k_j.ddf-g1\"@bdf-g.sdf.df")
        if (m.matches()) {
            println("Local - " + m.group("local"))
            println("Domain - " + m.group("domain"))
            println("Domain1 - " + m.group("domain1"))
            println("Domain2 - " + m.group("domain2"))
            for (i in 0..m.groupCount() - 1) {
                val group = m.group(i)
                println("group $i - $group")
            }
        }
    } catch (e: Exception) {
        println(e.message)
    }
}

fun test(cls: SomeInterface) {
    cls.f()
}

fun test1(): SomeInterface? {
    return null
}
