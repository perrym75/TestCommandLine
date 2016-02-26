package com.trustverse.factoryconsumer

import com.trustverse.config.ParamsConfig
import java.util.regex.Pattern

fun main(args: Array<String>) {
    try {
        val params = ParamsConfig(args)
        params.print()

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
