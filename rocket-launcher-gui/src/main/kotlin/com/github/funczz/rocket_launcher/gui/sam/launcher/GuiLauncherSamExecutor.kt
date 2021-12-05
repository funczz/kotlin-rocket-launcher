package com.github.funczz.rocket_launcher.gui.sam.launcher

import com.github.funczz.kotlin.sam.ISamExecutor
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModelPresent
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamStateNextAction

object GuiLauncherSamExecutor : ISamExecutor<
        LauncherSamActionInputData,
        LauncherSamModel,
        LauncherSamModelPresent,
        LauncherSamStateNextAction,
        GuiLauncherSamStateRepresentation
        > {

    private val samPresent = LauncherSamModelPresent()

    private val samNextAction = LauncherSamStateNextAction()

    private val samStateRepresentation = GuiLauncherSamStateRepresentation()

    override fun samPresent(): LauncherSamModelPresent {
        return samPresent
    }

    override fun samNextAction(): LauncherSamStateNextAction {
        return samNextAction
    }

    override fun samRepresentation(): GuiLauncherSamStateRepresentation {
        return samStateRepresentation
    }

}