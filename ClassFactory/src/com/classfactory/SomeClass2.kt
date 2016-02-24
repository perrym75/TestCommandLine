package com.classfactory

/**
 * Created by g.minkailov on 01.02.2016.
 */
class SomeClass2 protected constructor() : SomeInterface {

    override fun f(): SomeInterface {
        println(this.javaClass.getCanonicalName())
        return this
    }
}
