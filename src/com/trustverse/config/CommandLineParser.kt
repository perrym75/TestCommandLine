package com.trustverse.config

import com.sun.javaws.exceptions.InvalidArgumentException
import java.util.*
import java.util.regex.Pattern
import com.trustverse.utils.toBoolean

/**
 * Created by g.minkailov on 25.02.2016.
 */

class ParamInfo(val Name: String, val WithValue: Boolean, var Type: String = "", var Value: Any? = null): Comparable<ParamInfo> {
    var Presents: Boolean = false

    init {
        if (WithValue && Type.equals("")) {
            throw IllegalArgumentException("Parameter with value must have type.")
        }
    }
    /*override fun hashCode(): Int {
        return Name.hashCode()
    }

    override fun toString(): String {
        return Name
    }*/

    override fun compareTo(other: ParamInfo): Int {
        return Name.compareTo(other.Name)
    }
}

class CommandLineParser(private val args: Array<String>, val Params: List<ParamInfo>) {
    companion object {
        private val paramMatcher = Pattern.compile("-\\w+")

        fun isParam(str: String):Boolean {
            val m = paramMatcher.matcher(str)
            return m.matches()
        }
    }

    val UnnamedParams = ArrayList<String>()

    init {
        process()
    }

    operator fun get(param: String): ParamInfo? {
        return Params.find({ x -> x.Name.equals(param) && x.Presents })
    }

    private fun process() {
        var i = 0
        while (i < args.size) {
            try {
                var param = args[i]
                var value = if (i + 1 < args.size) args[i + 1] else ""

                if (isParam(param)) {
                    val paramInfo = Params.find({ x -> x.Name.equals(param) })
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
                                throw IllegalArgumentException("Invalid \"$param\" param value \"$value\". Value must be of type ${paramInfo.Type}.")
                            }
                        }
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