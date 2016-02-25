package com.trustverse.config

/**
 * Created by g.minkailov on 25.02.2016.
 */

class ParamInfo(val Name: String, val WithValue: Boolean, var Type: String = "", var Value: Any? = null): Comparable<ParamInfo> {
    var Presents: Boolean = false
    internal set

    init {
        if (WithValue && Type.equals("")) {
            throw IllegalArgumentException("Parameter with value must have type.")
        }
    }

    override fun compareTo(other: ParamInfo): Int {
        return Name.compareTo(other.Name)
    }
}