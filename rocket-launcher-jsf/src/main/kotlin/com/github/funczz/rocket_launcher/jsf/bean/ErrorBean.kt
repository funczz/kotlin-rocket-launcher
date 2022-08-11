package com.github.funczz.rocket_launcher.jsf.bean

import java.io.Serializable
import javax.enterprise.context.SessionScoped
import javax.inject.Named

@Named
@SessionScoped
open class ErrorBean : Serializable {

    open var errorMassage: String = ""

    open fun errorMassage(): String {
        return errorMassage
    }

    open fun error(message: String): String {
        errorMassage = message
        return "error"
    }

    companion object {
        private const val serialVersionUID: Long = -3547673681173710389L
    }

}