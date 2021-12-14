package com.github.funczz.rocket_launcher.jsf.sam.launcher

import com.github.funczz.kotlin.sam.ISamStateRepresentation
import com.github.funczz.rocket_launcher.core.sam.launcher.ILauncherSamState
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel

open class JsfLauncherSamStateRepresentation : ILauncherSamState, ISamStateRepresentation<LauncherSamModel> {

    open var returnValue: String? = null

    override fun representation(model: LauncherSamModel) {
        returnValue = when {
            isReady(model) -> "index"
            isTransitionToCounting(model) -> "counting"
            isAborted(model) -> "aborted"
            isLaunched(model) -> "launched"
            else -> null
        }
    }

}