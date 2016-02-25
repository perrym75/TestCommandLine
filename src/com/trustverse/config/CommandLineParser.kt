package com.trustverse.config

import java.util.*
import java.util.regex.Pattern
import com.trustverse.utils.toBoolean

class CommandLineParser(private val args: Array<String>,
                        val Params: List<ParamInfo>,
                        private var isParam: (str: String) -> Boolean = { x -> x.startsWith("-")}) {
    val UnnamedParams = ArrayList<String>()

    init {
        process()
    }

    operator fun get(param: String): ParamInfo? = Params.find({ x -> x.Name.equals(param) && x.Presents })

    private fun process() {
        var i = 0
        while (i < args.size) {
            try {
                var param = args[i]
                var value = if (i + 1 < args.size) args[i + 1] else ""

                if (isParam?.invoke(param) ?: true) {
                    val paramInfo = Params.find({ x -> x.Name.equals(param) })
                    if (paramInfo != null) {
                        paramInfo.Presents = true

                        if (paramInfo.WithValue) {
                            try {
                                if (isParam?.invoke(value) ?: true) {
                                    paramInfo.Value = null
                                    value = ""
                                    throw Exception()
                                } else {
                                    paramInfo.Value = convertValue(paramInfo.Type, value)
                                    i++
                                }
                            } catch (e: Exception) {
                                throw IllegalArgumentException("Invalid \"$param\" param value \"$value\". Value must be of type ${paramInfo.Type}.")
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