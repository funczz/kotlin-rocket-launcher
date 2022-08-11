package com.github.funczz.rocket_launcher.gui.sam.launcher

import com.github.funczz.kotlin.sam.ISamExecutor
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModelPresent
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamStateNextAction
import com.github.funczz.rocket_launcher.gui.util.ExtendedJFrame

object GuiLauncherSamExecutor : ISamExecutor<
        LauncherSamActionInputData,
        LauncherSamModel,
        ExtendedJFrame,
        Unit,
        > {

    private val samPresent = LauncherSamModelPresent::present

    private val samNextAction = LauncherSamStateNextAction::nextAction

    private val samStateRepresentation = GuiLauncherSamStateRepresentation::representation

    override fun samPresent(): (LauncherSamActionInputData) -> Result<LauncherSamModel> {
        return samPresent
    }

    override fun samNextAction(): (LauncherSamModel) -> Result<LauncherSamModel> {
        return samNextAction
    }

    override fun samRepresentation(): (LauncherSamModel, ExtendedJFrame) -> Result<Unit> {
        return samStateRepresentation
    }

}