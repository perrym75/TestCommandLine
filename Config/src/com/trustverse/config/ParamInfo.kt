package com.trustverse.config

/**
 * Created by g.minkailov on 25.02.2016.
 */

class ParamInfo(val Name: String, val WithValue: Boolean, val Type: Class<*> = Any::class.java, internal var setter: ((value: Any) -> Unit)? = null) {
    var Presents: Boolean = false
    internal set

    init {
        if (WithValue && setter == null) {
            throw IllegalArgumentException("Parameter with value must have type.")
        }
    }
}