package io.kotlintest.provided.com.github.funczz.rocket_launcher.core.sam.launcher

import com.github.funczz.kotlin.sam.ISamExecutor
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamActionInputData
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModel
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamModelPresent
import com.github.funczz.rocket_launcher.core.sam.launcher.LauncherSamStateNextAction

class ResultLauncherSamExecutor(
    private val samStateRepresentation: ResultLauncherSamStateRepresentation
) : ISamExecutor<
        LauncherSamActionInputData,
        LauncherSamModel,
        LauncherSamModelPresent,
        LauncherSamStateNextAction,
        ResultLauncherSamStateRepresentation
        > {

    private val samPresent = LauncherSamModelPresent()

    private val samNextAction = LauncherSamStateNextAction()

    override fun samPresent(): LauncherSamModelPresent {
        return samPresent
    }

    override fun samNextAction(): LauncherSamStateNextAction {
        return samNextAction
    }

    override fun samRepresentation(): ResultLauncherSamStateRepresentation {
        return samStateRepresentation
    }

}