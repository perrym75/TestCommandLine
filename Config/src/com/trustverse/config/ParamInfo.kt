package com.trustverse.config

/**
 * Created by g.minkailov on 25.02.2016.
 */

class ParamInfo(val Name: String, val WithValue: Boolean, var Type: Class<*> = Any::class.java, var Value: Any? = null) {
    var Presents: Boolean = false
    internal set

    init {
        if (WithValue && Type.equals("")) {
            throw IllegalArgumentException("Parameter with value must have type.")
        }
    }
}