package com.github.funczz.rocket_launcher.gui.sam.launcher

import com.github.funczz.kotlin.sam.ISamModelPresent
import com.github.funczz.kotlin.sam.ISamStateNextAction
import com.github.funczz.kotlin.sam.SamNextAction
import com.github.funczz.rocket_launcher.core.domain.model.launcher.Launcher
import com.github.funczz.rocket_launcher.gui.sam.launcher.action.LaunchLauncherSamAction

class LauncherSamStateNextAction : ILauncherSamState, ISamStateNextAction<LauncherSamModel> {

    private val samPresent: ISamModelPresent<LauncherSamActionInputData, LauncherSamModel> = LauncherSamModelPresent()

    override fun nextAction(model: LauncherSamModel): Result<SamNextAction<LauncherSamModel>> {
        val nextAction = when(isCountingZero(model = model)) {
            true -> {
                val prevModel = Launcher(state = model.state, counter = model.counter)
                val data = LauncherSamActionInputData(model = prevModel)
                val nextModel = LaunchLauncherSamAction.execute(samPresent::present, data)
                    .fold(
                        onSuccess = { it },
                        onFailure = { return Result.failure(it) }
                    )
                SamNextAction.continued(model = nextModel)
            }
            else -> SamNextAction.terminated(model = model)
        }
        return Result.success(nextAction)
    }

}