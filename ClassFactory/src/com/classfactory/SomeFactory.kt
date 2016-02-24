package com.classfactory

/**
 * Created by g.minkailov on 01.02.2016.
 */
object SomeFactory {
    @Throws(IllegalAccessException::class, InstantiationException::class, IllegalArgumentException::class)
    fun newInstance(cls: Class<out SomeInterface>): SomeInterface {
            return cls.newInstance()
    }
}
