package com.github.funczz.rocket_launcher.jsf.bean

import java.io.Serializable
import java.util.*
import javax.enterprise.context.SessionScoped
import javax.inject.Named

@Named
@SessionScoped
open class AbortedBean : Serializable {

    open var currentCounter: Optional<UInt> = Optional.empty()

    companion object {
        private const val serialVersionUID: Long = -3992171317618865364L
    }

}