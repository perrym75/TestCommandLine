/**
 * Created by g.minkailov on 25.02.2016.
 * @author Goussein Minkailov
 */

package com.trustverse.config

class ParamInfo(val Name: String, val WithValue: Boolean, val Type: Class<*> = Any::class.java,
                internal val setter: ((value: Any) -> Unit)? = null) {
    var Presents: Boolean = false
    internal set

    init {
        if (WithValue && setter == null) {
            throw IllegalArgumentException("Parameter with value must have type.")
        }
    }
}