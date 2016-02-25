package com.trustverse.config

import java.util.*
import com.trustverse.utils.toType

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
                                    value = ""
                                    throw Exception()
                                } else {
                                    paramInfo.setter?.invoke(value.toType(paramInfo.Type))
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
