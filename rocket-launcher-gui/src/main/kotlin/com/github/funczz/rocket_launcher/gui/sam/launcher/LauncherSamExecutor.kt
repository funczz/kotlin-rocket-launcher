package com.github.funczz.rocket_launcher.gui.sam.launcher

import com.github.funczz.kotlin.sam.ISamExecutor

object LauncherSamExecutor : ISamExecutor<
        LauncherSamActionInputData,
        LauncherSamModel,
        LauncherSamModelPresent,
        LauncherSamStateNextAction,
        LauncherSamStateRepresentation
        > {

    private val samPresent = LauncherSamModelPresent()

    private val samNextAction = LauncherSamStateNextAction()

    private val samStateRepresentation = LauncherSamStateRepresentation()

    override fun samPresent(): LauncherSamModelPresent {
        return samPresent
    }

    override fun samNextAction(): LauncherSamStateNextAction {
        return samNextAction
    }

    override fun samRepresentation(): LauncherSamStateRepresentation {
        return samStateRepresentation
    }

}