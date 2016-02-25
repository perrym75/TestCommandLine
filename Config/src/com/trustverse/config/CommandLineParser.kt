package com.trustverse.config

import java.util.*
import com.trustverse.utils.toBoolean

class CommandLineParser(private val args: Array<String>,
                        params: List<ParamInfo>,
                        private var isParam: (str: String) -> Boolean = { x -> x.startsWith("-")}) {
    val UnnamedParams = ArrayList<String>()
    val Params = HashMap<String, ParamInfo>()

    init {
        Params.putAll(params.map({ x -> Pair(x.Name, x) }))
        process()
    }

    operator fun get(param: String): ParamInfo? {
        val paramInfo = Params[param]
        return if (paramInfo != null && paramInfo.Presents) paramInfo else null
    }

    private fun process() {
        var i = 0
        while (i < args.size) {
            try {
                var param = args[i]
                var value = if (i + 1 < args.size) args[i + 1] else ""

                if (isParam(param)) {
                    val paramInfo = Params[param]
                    if (paramInfo != null) {
                        paramInfo.Presents = true

                        if (paramInfo.WithValue) {
                            try {
                                if (isParam(value)) {
                                    paramInfo.Value = null
                                    value = ""
                                    throw Exception()
                                } else {
                                    paramInfo.Value = convertValue(paramInfo.Type, value)
                                    i++
                                }
                            } catch (e: Exception) {
                                throw IllegalArgumentException("Invalid \"$param\" param value \"$value\". Value must be of ${paramInfo.Type} type.")
                            }
                        }
                    } else {
                        throw IllegalArgumentException("Unknown parameter \"$param\" specified.")
                    }
                } else {
                    UnnamedParams.add(param)
                }

                i++
            }
            catch (e: Exception) {
                throw e
            }
        }
    }
}

private fun convertValue(type: String, value: String): Any {
    when (type) {
        "String" -> return value
        "Boolean" -> return value.toBoolean()
        "Integer" -> return value.toInt()
        "Double" -> return value.toDouble()
        else -> throw IllegalArgumentException("Invalid \"type\" argument value.")
    }
}